package com.doan.apidoan.controllers;

import com.doan.apidoan.config.JwtUtilities;
import com.doan.apidoan.dtos.RoomDTO;
import com.doan.apidoan.models.RoomUser;
import com.doan.apidoan.models.Users;
import com.doan.apidoan.services.RoomService;
import com.doan.apidoan.services.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class RoomController {
    private final RoomService roomService;
    private final JwtUtilities jwtUtilities;
    private final UserService userService;
    @GetMapping("/rooms")
    public ResponseEntity<List<RoomDTO>> getRoomListWithoutPaging(@RequestParam(value = "roomRank", required = false) String rank,
                                                                  @RequestParam(value = "roomType", required = false) String type,
                                                                  @RequestParam(value = "sortBy", required = false) String sortBy) {
        List<RoomDTO> dto = roomService.findAll(rank, type, sortBy);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }
    @GetMapping("/rooms/{roomCode}")
    public ResponseEntity<RoomDTO> getRoomRoomCode(@PathVariable("roomCode") String roomCode) {
        RoomDTO dto = roomService.getRoomByRoomCode(roomCode);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }
    @PostMapping("/rooms/{roomCode}/{startDate}/{endDate}")
    public ResponseEntity<?> bookRoomByUser(@PathVariable("roomCode") String roomCode,
                                            @PathVariable("endDate") String endDate,
                                            @PathVariable("startDate") String startDate,
                                            HttpServletRequest request) {
        String authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String token = authorizationHeader.substring(7);
            String username = jwtUtilities.extractUsername(token);
            Users user = userService.findUserByUsername(username);
            roomService.bookRoomByUser(user, roomCode, startDate, endDate);
            return new ResponseEntity<>("Đã đặt phòng thành công", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Có lỗi xảy ra khi đặt phòng", HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping("/user-room")
    public ResponseEntity<List<RoomDTO>> getRoomByUser(HttpServletRequest request) {
        String authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String token = authorizationHeader.substring(7);
            String username = jwtUtilities.extractUsername(token);
            Users user = userService.findUserByUsername(username);
            List<RoomDTO> roomDTOList = roomService.getBookingRoomByUserId(user.getUserId());
            return new ResponseEntity<>(roomDTOList, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }
}
