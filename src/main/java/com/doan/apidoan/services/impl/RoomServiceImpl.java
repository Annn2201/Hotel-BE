package com.doan.apidoan.services.impl;

import com.doan.apidoan.dtos.PagingDTO;
import com.doan.apidoan.dtos.RoomDTO;
import com.doan.apidoan.exceptions.CustomException;
import com.doan.apidoan.models.RoomRanks;
import com.doan.apidoan.models.RoomTypes;
import com.doan.apidoan.models.Rooms;
import com.doan.apidoan.models.Users;
import com.doan.apidoan.repositories.RoomRankRepository;
import com.doan.apidoan.repositories.RoomRepository;
import com.doan.apidoan.repositories.RoomTypeRepository;
import com.doan.apidoan.services.RoomService;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.data.auditing.CurrentDateTimeProvider;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RoomServiceImpl implements RoomService {
    private final RoomRepository roomRepository;
    private final RoomRankRepository rankRepository;
    private final RoomTypeRepository typeRepository;
    public List<RoomDTO> findAll(String rankName, String typeName) {
        Specification<Rooms> spec = (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (rankName != null) {
                RoomRanks rank = rankRepository.findByRankName(rankName)
                        .orElseThrow(() -> new CustomException("Không thấy rank này"));
                predicates.add(criteriaBuilder.equal(root.get("roomRank"), rank));
            }

            if (typeName != null) {
                RoomTypes type = typeRepository.findByTypeName(typeName)
                        .orElseThrow(() -> new CustomException("Không thấy type này"));
                predicates.add(criteriaBuilder.equal(root.get("roomType"), type));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
        List<Rooms> rooms = roomRepository.findAll(spec);
        List<RoomDTO> roomDTOList = rooms.stream()
                .map(q -> RoomDTO.builder()
                        .roomName(q.getRoomName())
                        .roomCode(q.getRoomCode())
                        .roomRank(q.getRoomRank().getRankName())
                        .roomType(q.getRoomType().getTypeName())
                        .pricePerNight(q.getPricePerNight())
                        .description(q.getDescription())
                        .build())
                .collect(Collectors.toList());
        return roomDTOList;
    }

    @Override
    public RoomDTO getRoomByRoomCode(String roomCode) {
        Rooms room = roomRepository.findByRoomCode(roomCode).orElseThrow(() -> new CustomException("Khoont thấy phòng này", HttpStatus.BAD_REQUEST));
        RoomDTO roomDTO = RoomDTO.builder()
                .roomName(room.getRoomName())
                .roomType(room.getRoomType().getTypeName())
                .roomRank(room.getRoomRank().getRankName())
                .description(room.getDescription())
                .pricePerNight(room.getPricePerNight())
                .build();
        return roomDTO;
    }

    @Override
    public void bookRoomByUser(Users users, String roomCode) {
        LocalDateTime currentDate = LocalDateTime.now();
        LocalDateTime checkInTime = currentDate.plus(2, ChronoUnit.DAYS);
        Rooms rooms = roomRepository.findByRoomCode(roomCode).orElseThrow(() -> new CustomException("Không tìm thấy phòng", HttpStatus.BAD_REQUEST));
        rooms.setUser(users);
        rooms.setCheckInTime(checkInTime);
        rooms.setCheckOutTime(checkInTime.plus(2, ChronoUnit.DAYS));
        roomRepository.save(rooms);
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
