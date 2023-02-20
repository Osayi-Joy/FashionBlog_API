package com.jconcept.fashionblog.DTO.response;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Getter
@Setter
public class BaseResponse<T> {
    private String message;
    private LocalDateTime timestamp = LocalDateTime.now();;

    private HttpStatus status;
    private T data;

    public BaseResponse(HttpStatus status) {
        this.status = status;
    }
}
