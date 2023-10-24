package com.doan.apidoan.controllers;

import com.doan.apidoan.config.JwtUtilities;
import com.doan.apidoan.dtos.UserDTO;
import com.doan.apidoan.models.Users;
import com.doan.apidoan.services.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class UserController {
    private final JwtUtilities jwtUtilities;
    private final UserService userService;
    @GetMapping("/user")
    public ResponseEntity<UserDTO> getUserByUsername(HttpServletRequest request) {
        String authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String token = authorizationHeader.substring(7);
            String username = jwtUtilities.extractUsername(token);
            Users user = userService.findUserByUsername(username);
            UserDTO userDTO = new UserDTO();
            BeanUtils.copyProperties(user, userDTO);
            return new ResponseEntity<>(userDTO, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/user")
    public ResponseEntity<?> updateUserDetail(@RequestBody UserDTO userDTO,
                                              HttpServletRequest request) {
        String authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String token = authorizationHeader.substring(7);
            String username = jwtUtilities.extractUsername(token);
            Users user = userService.findUserByUsername(username);
            userService.updateUserDetail(user, userDTO);
            return new ResponseEntity<>("Đã cập nhật thông tin người dùng", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Có lỗi xảy ra trong quá trình cập nhật", HttpStatus.BAD_REQUEST);
        }
    }
}
