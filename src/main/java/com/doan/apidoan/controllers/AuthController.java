package com.doan.apidoan.controllers;

import com.doan.apidoan.dtos.LoginDTO;
import com.doan.apidoan.dtos.RegisterDTO;
import com.doan.apidoan.dtos.UserDTO;
import com.doan.apidoan.models.Users;
import com.doan.apidoan.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class AuthController {
    private final UserService userService;
    @PostMapping("/register")
    ResponseEntity<?> registerUser(@RequestBody RegisterDTO registerDTO) {
        Users user = new Users();
        BeanUtils.copyProperties(registerDTO, user);
        return userService.saveUser(user);
    }

    @PostMapping("/login")
    public ResponseEntity<UserDTO> getDetailUser(@RequestBody LoginDTO loginDTO) {
        UserDTO loginedUser = userService.getDetailUser(loginDTO);
        if (loginedUser != (null)) {
            return new ResponseEntity<>(loginedUser, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

}
