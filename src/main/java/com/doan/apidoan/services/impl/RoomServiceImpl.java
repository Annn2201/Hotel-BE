package com.doan.apidoan.services.impl;

import com.doan.apidoan.dtos.PagingDTO;
import com.doan.apidoan.dtos.RoomDTO;
import com.doan.apidoan.exceptions.CustomException;
import com.doan.apidoan.models.*;
import com.doan.apidoan.repositories.RoomRankRepository;
import com.doan.apidoan.repositories.RoomRepository;
import com.doan.apidoan.repositories.RoomTypeRepository;
import com.doan.apidoan.repositories.RoomUserRepository;
import com.doan.apidoan.services.RoomService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class RoomServiceImpl implements RoomService {
    private final RoomRepository roomRepository;
    private final RoomRankRepository rankRepository;
    private final RoomTypeRepository typeRepository;
    private final RoomUserRepository roomUserRepository;
    private final ObjectMapper objectMapper;
    public List<RoomDTO> findAll(String rankName, String typeName, String sortBy) {
        Specification<Rooms> spec = (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (rankName != null) {
                RoomRanks rank = rankRepository.findByRankName(rankName)
                        .orElseThrow(() -> new CustomException("Khong tim thay hang phong nay", HttpStatus.BAD_REQUEST));
                predicates.add(criteriaBuilder.equal(root.get("roomRank"), rank));
            }

            if (typeName != null) {
                RoomTypes type = typeRepository.findByTypeName(typeName)
                        .orElseThrow(() -> new CustomException("Khong tim thay loai phong nay", HttpStatus.BAD_REQUEST));
                predicates.add(criteriaBuilder.equal(root.get("roomType"), type));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
        List<Rooms> rooms = roomRepository.findAll(spec);
        List<RoomDTO> roomDTOs = rooms.stream()
                .map(q -> {
                    List<String> imageUrls = q.getRoomImage().stream()
                            .map(Image::getImageUrl)
                            .collect(Collectors.toList());
                    return RoomDTO.builder()
                            .roomName(q.getRoomName())
                            .roomCode(q.getRoomCode())
                            .roomRank(q.getRoomRank().getRankName())
                            .roomType(q.getRoomType().getTypeName())
                            .pricePerNight(q.getPricePerNight())
                            .description(q.getDescription())
                            .population(q.getPopulation())
                            .images(imageUrls)
                            .build();
                })
                .collect(Collectors.toList());

        if ("population".equals(sortBy)) {
            roomDTOs.sort(Comparator.comparing(RoomDTO::getPopulation).reversed());
        } else if ("otherField".equals(sortBy)) {
        }
        return roomDTOs;

    }

    @Override
    public RoomDTO getRoomByRoomCode(String roomCode) {
        Rooms room = roomRepository.findByRoomCode(roomCode).orElseThrow(() -> new CustomException("Khoont thấy phòng này", HttpStatus.BAD_REQUEST));
        List<Image> imageList = room.getRoomImage();
        List<String> imageUrl = new ArrayList<>();
        imageList.forEach(image -> {
            imageUrl.add(image.getImageUrl());
        });
        return RoomDTO.builder()
                .roomCode(room.getRoomCode())
                .roomName(room.getRoomName())
                .roomType(room.getRoomType().getTypeName())
                .roomRank(room.getRoomRank().getRankName())
                .description(room.getDescription())
                .pricePerNight(room.getPricePerNight())
                .population(room.getPopulation())
                .images(imageUrl)
                .build();
    }

    @Override
    public void bookRoomByUser(Users users, String roomCode, String startDate, String endDate) {
        AtomicInteger countAvailableDay = new AtomicInteger();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEE MMM dd yyyy");
        LocalDate localStartDate = LocalDate.parse(startDate,formatter);
        LocalDate localEndDate = LocalDate.parse(endDate,formatter);
        Rooms rooms = roomRepository.findByRoomCode(roomCode).orElseThrow(() -> new CustomException("Không tìm thấy phòng", HttpStatus.BAD_REQUEST));
        List<RoomUser> roomUserList = roomUserRepository.findByRoomsId(rooms.getRoomId());
        if (roomUserList.isEmpty()) {
            RoomUser newRoomUser = new RoomUser();
            newRoomUser.setUsersId(users.getUserId());
            newRoomUser.setRoomsId(rooms.getRoomId());
            newRoomUser.setStartDate(localStartDate);
            newRoomUser.setEndDate(localEndDate);
            roomUserRepository.save(newRoomUser);
        } else {
            roomUserList.forEach(roomUser -> {
                if(localStartDate.isEqual(roomUser.getEndDate())
                || localStartDate.isAfter(roomUser.getEndDate())) {
                    countAvailableDay.getAndIncrement();
                }
            });
            if (countAvailableDay.get() != 0) {
                RoomUser newRoomUser = new RoomUser();
                newRoomUser.setUsersId(users.getUserId());
                newRoomUser.setRoomsId(rooms.getRoomId());
                newRoomUser.setStartDate(localStartDate);
                newRoomUser.setEndDate(localEndDate);
                rooms.setPopulation(rooms.getPopulation() + 1);
                roomUserRepository.save(newRoomUser);
            } else {
                throw new CustomException("Phòng đã có người đặt vào thời gian này", HttpStatus.BAD_REQUEST);
            }
        }
    }

    @Override
    public List<RoomDTO> getBookingRoomByUserId(Long id) {
        List<RoomDTO> roomDTOList = new ArrayList<>();
        List<RoomUser> roomUserList = roomUserRepository.findByUsersId(id);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEE MMM dd yyyy");
        roomUserList.forEach(roomUser -> {
            Rooms room = roomRepository.findById(roomUser.getRoomsId()).orElseThrow();
            List<Image> imageList = room.getRoomImage();
            List<String> imageUrl = new ArrayList<>();
            imageList.forEach(image -> {
                imageUrl.add(image.getImageUrl());
            });
            RoomDTO roomDTO = RoomDTO.builder()
                    .roomCode(room.getRoomCode())
                    .roomName(room.getRoomName())
                    .roomType(room.getRoomType().getTypeName())
                    .roomRank(room.getRoomRank().getRankName())
                    .description(room.getDescription())
                    .pricePerNight(room.getPricePerNight())
                    .population(room.getPopulation())
                    .images(imageUrl)
                    .startDate(roomUser.getStartDate().format(formatter))
                    .endDate(roomUser.getEndDate().format(formatter))
                    .build();
            roomDTOList.add(roomDTO);
        });
        return roomDTOList;
    }

    private PagingDTO<List<RoomDTO>> getPagingDTO(Page<Rooms> rooms) {
        List<RoomDTO> roomDTOList = rooms.getContent().stream()
                .map(q -> RoomDTO.builder()
                        .roomCode(q.getRoomCode())
                        .roomRank(q.getRoomRank().getRankName())
                        .roomType(q.getRoomType().getTypeName())
                        .pricePerNight(q.getPricePerNight())
                        .description(q.getDescription())
                        .build())
                .collect(Collectors.toList());
        return new PagingDTO(roomDTOList, rooms.getTotalPages(), rooms.getTotalElements());
    }
}
