package com.jconcept.fashionblog.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Role {
    CUSTOMER("ROLE_CUSTOMER"), DESIGNER("ROLE_DESIGNER");

    private final String role;

}
