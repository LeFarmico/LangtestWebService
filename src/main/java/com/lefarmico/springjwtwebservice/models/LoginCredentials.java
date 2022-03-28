package com.lefarmico.springjwtwebservice.models;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class LoginCredentials {

    private String email;
    private String password;
}
