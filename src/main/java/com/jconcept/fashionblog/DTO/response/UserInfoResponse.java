package com.jconcept.fashionblog.DTO.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
public class UserInfoResponse {
        private Long id;
        private String username;
        private String role;
}
