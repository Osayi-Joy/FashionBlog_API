package com.jconcept.fashionblog.exception;

import com.jconcept.fashionblog.DTO.response.BaseResponse;
import com.jconcept.fashionblog.util.ApiResponseUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class ExceptionController extends ResponseEntityExceptionHandler {
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<BaseResponse> userNotFoundException(UserNotFoundException exception){
        return ApiResponseUtil.errorResponse(HttpStatus.NOT_FOUND, exception.getMessage());
    }

    @ExceptionHandler(PostAlreadyLikedException.class)
    public ResponseEntity<BaseResponse> postAlreadyLikedException(PostAlreadyLikedException exception){
        return ApiResponseUtil.errorResponse(HttpStatus.FORBIDDEN, exception.getMessage());
    }

    @ExceptionHandler(PostNotFoundException.class)
    public ResponseEntity<BaseResponse> postNotFoundException(PostNotFoundException exception){
        return ApiResponseUtil.errorResponse(HttpStatus.NOT_FOUND, exception.getMessage());
    }

    @ExceptionHandler(UserAlreadyExistException.class)
    public ResponseEntity<BaseResponse> userAlreadyLikedException(UserAlreadyExistException exception){
        return ApiResponseUtil.errorResponse(HttpStatus.FORBIDDEN, exception.getMessage());
    }

}
