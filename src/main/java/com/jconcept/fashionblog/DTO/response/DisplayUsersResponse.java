package com.jconcept.fashionblog.DTO.response;

import com.jconcept.fashionblog.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@Getter
@RequiredArgsConstructor
public class DisplayUsersResponse {
    private String message;
    private LocalDateTime timeStamp;
    List<User> users = null;

}
