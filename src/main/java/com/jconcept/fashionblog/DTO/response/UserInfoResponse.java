package com.jconcept.fashionblog.DTO.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UserInfoResponse {
        private String username;
        private String email;
}
