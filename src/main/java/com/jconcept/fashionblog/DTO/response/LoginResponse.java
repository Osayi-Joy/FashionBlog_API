package com.jconcept.fashionblog.DTO.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
@Builder @AllArgsConstructor @Getter @Setter
public class LoginResponse {
    private String message;
    private LocalDateTime timeStamp;
}
