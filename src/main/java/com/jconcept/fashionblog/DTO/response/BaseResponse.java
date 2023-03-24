package com.jconcept.fashionblog.DTO.response;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class BaseResponse<T> {
    private String message;
    private HttpStatus status;
    private T data;
    private LocalDateTime timestamp = LocalDateTime.now();;

    public BaseResponse(HttpStatus status) {
        this.status = status;
    }

    public BaseResponse(String message, HttpStatus status, T data, LocalDateTime timestamp) {
        this.message = message;
        this.status = status;
        this.data = data;
        this.timestamp = timestamp;
    }

    public BaseResponse(String message, HttpStatus status, T data) {
        this.message = message;
        this.status = status;
        this.data = data;
    }
}
