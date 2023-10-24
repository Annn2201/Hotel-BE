package com.doan.apidoan.dtos;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDTO {
    private String firstName;
    private String lastName;
    private String phone;
    private String email;
    private String password;
    private String identifyNumber;
}
