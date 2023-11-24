package com.doan.apidoan.services;

import com.doan.apidoan.dtos.ChangePasswordDTO;
import com.doan.apidoan.dtos.UserDTO;
import com.doan.apidoan.models.Users;
import org.springframework.http.ResponseEntity;

public interface UserService {
    ResponseEntity<?> saveUser(Users user);
    void updateUserDetail(Users user, UserDTO userDTO);
    Users findUserByUsername(String username);

    void changePassword(Users users, ChangePasswordDTO changePasswordDTO);
}
