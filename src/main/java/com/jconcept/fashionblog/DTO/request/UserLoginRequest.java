package com.jconcept.fashionblog.DTO.request;

import lombok.*;

@Builder @AllArgsConstructor @NoArgsConstructor
@Getter @Setter
public class UserLoginRequest {
    private String email;
    private String password;
}
