package com.doan.apidoan.dtos;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RegisterDTO {
    private String lastName;
    private String firstName;
    private String email;
    private String identifyNumber;
    private String username;
    private String password;
}
