package com.doan.apidoan.services.impl;

import com.doan.apidoan.dtos.LoginDTO;
import com.doan.apidoan.dtos.UserDTO;
import com.doan.apidoan.dtos.response.ResponseMessage;
import com.doan.apidoan.exceptions.CustomException;
import com.doan.apidoan.models.Users;
import com.doan.apidoan.repositories.UserReporitory;
import com.doan.apidoan.services.UserService;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.User;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserReporitory userReporitory;
    @Override
    public ResponseEntity<?> saveUser(Users user) {
       userReporitory.save(user);
        return ResponseEntity.ok().body(new ResponseMessage<>("Dang ki thanh cong" ,HttpStatus.OK));
    }

    @Override
    public UserDTO getDetailUser(LoginDTO loginDTO) {
        Users user = userReporitory.findByUsername(loginDTO.getUsername()).orElseThrow(() -> new CustomException("Khong tim thay user"));
        if (!loginDTO.getPassword().equals(user.getPassword())) {
            throw new CustomException("Sai mật khẩu");
        }else {
            UserDTO userDTO = new UserDTO();
            BeanUtils.copyProperties(user, userDTO);
            return userDTO;
        }
    }
}
