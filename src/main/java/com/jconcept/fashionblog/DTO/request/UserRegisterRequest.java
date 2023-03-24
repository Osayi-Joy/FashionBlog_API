package com.jconcept.fashionblog.DTO.request;

import lombok.*;

@Getter @Setter @AllArgsConstructor @NoArgsConstructor
public class UserRegisterRequest {
    private String name;
    private String email;
    private String role;
    private String password;
    private String confirmPassword;
}
