package com.jconcept.fashionblog.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResetPasswordDto {
    private String email;
    private String newPassword;
    private String confirmPassword;
}
