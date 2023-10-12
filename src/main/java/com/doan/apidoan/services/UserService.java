package com.doan.apidoan.services;

import com.doan.apidoan.dtos.LoginDTO;
import com.doan.apidoan.dtos.UserDTO;
import com.doan.apidoan.models.Users;
import org.springframework.http.ResponseEntity;

public interface UserService {
    ResponseEntity<?> saveUser(Users user);
    UserDTO getDetailUser(LoginDTO loginDTO);
    Users findUserByUsername(String username);
}
