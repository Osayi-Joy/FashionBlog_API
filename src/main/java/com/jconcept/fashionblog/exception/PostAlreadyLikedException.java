package com.jconcept.fashionblog.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.stereotype.Component;

@Getter
@Builder
public class PostAlreadyLikedException extends RuntimeException{
    private String message;

    public PostAlreadyLikedException(String message) {
        this.message = message;
    }
}
