package com.jconcept.fashionblog.DTO.response;

import lombok.*;
import org.springframework.http.HttpStatus;

@Builder
@Getter
@Setter
@AllArgsConstructor @NoArgsConstructor
public class ExceptionResponse {
    private String message;
    private HttpStatus status;
}
