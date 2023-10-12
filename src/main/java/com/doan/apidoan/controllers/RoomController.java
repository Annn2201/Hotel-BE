package com.doan.apidoan.controllers;

import com.doan.apidoan.config.JwtUtilities;
import com.doan.apidoan.dtos.RoomDTO;
import com.doan.apidoan.models.Users;
import com.doan.apidoan.services.RoomService;
import com.doan.apidoan.services.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class RoomController {
    private final RoomService roomService;
    private final JwtUtilities jwtUtilities;
    private final UserService userService;
//    @GetMapping("/rooms")
//    public ResponseEntity<PagingDTO<List<RoomDTO>>> getRoomList(@RequestParam("page") int page,
//                                                                @RequestParam("size") int size,
//                                                                @RequestParam(value = "keyword") String keyword,
//                                                                @RequestParam(value = "rank", required = false) RoomRanks rank,
//                                                                @RequestParam(value = "type", required = false) RoomTypes type) {
//        PagingDTO<List<RoomDTO>> dto = roomService.findAll(page, size, keyword, rank, type);
//        return new ResponseEntity<>(dto, HttpStatus.OK);
//    }
    @GetMapping("/rooms")
    public ResponseEntity<List<RoomDTO>> getRoomListWithoutPaging(@RequestParam(value = "roomRank", required = false) String rank,
                                                                  @RequestParam(value = "roomType", required = false) String type) {
        List<RoomDTO> dto = roomService.findAll(rank, type);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }
    @GetMapping("/rooms/{roomCode}")
    public ResponseEntity<RoomDTO> getRoomRoomCode(@PathVariable("roomCode") String roomCode) {
        RoomDTO dto = roomService.getRoomByRoomCode(roomCode);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }
    @PostMapping("/rooms/{roomCode}")
    public ResponseEntity<?> bookRoomByUser(@PathVariable("roomCode") String roomCode,
                                            HttpServletRequest request) {
        String authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String token = authorizationHeader.substring(7);
            String username = jwtUtilities.extractUsername(token);
            Users user = userService.findUserByUsername(username);
            roomService.bookRoomByUser(user, roomCode);
            return new ResponseEntity<>("Đã đặt phòng thành công", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Có lỗi xảy ra khi đặt phòng", HttpStatus.BAD_REQUEST);
        }
    }
}
