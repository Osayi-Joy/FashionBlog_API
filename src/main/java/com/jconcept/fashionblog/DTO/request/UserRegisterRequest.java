package com.jconcept.fashionblog.DTO.request;

import com.jconcept.fashionblog.entity.Role;
import lombok.*;

@Getter @Setter @AllArgsConstructor @NoArgsConstructor
public class UserRegisterRequest {
    private String name;
    private String email;
    private Role role;
    private String password;
}
