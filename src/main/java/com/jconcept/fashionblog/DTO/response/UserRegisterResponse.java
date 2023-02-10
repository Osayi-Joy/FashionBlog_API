package com.jconcept.fashionblog.DTO.response;

import com.jconcept.fashionblog.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@AllArgsConstructor @Getter @Setter @Builder
public class UserRegisterResponse {
    private String message;
    private LocalDateTime timeStamp;
    private User user;
}
