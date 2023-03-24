package com.jconcept.fashionblog.exception;

import lombok.Builder;
import lombok.Getter;
@Builder
@Getter
public class PostNotFoundException extends RuntimeException{
    private String message;

    public PostNotFoundException(String message) {
        this.message = message;
    }
}
