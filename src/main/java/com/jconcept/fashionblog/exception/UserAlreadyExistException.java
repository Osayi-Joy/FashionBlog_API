package com.jconcept.fashionblog.exception;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class UserAlreadyExistException extends RuntimeException {
    private String message;

}
