package com.jconcept.fashionblog.util;

import com.jconcept.fashionblog.DTO.response.BaseResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ApiResponseUtil {
    public static  <T> ResponseEntity<BaseResponse<T>> response(HttpStatus status, T data, String message ){
        return ApiResponseUtil.getResponse(status,data,message);
    }

    private static  <T> ResponseEntity<BaseResponse<T>> getResponse(HttpStatus status, T data, String message ){
        BaseResponse<T> ar = new BaseResponse<>(HttpStatus.OK);
        ar.setData(data);
        ar.setMessage(message);
        return new ResponseEntity<>(ar,status);
    }

    public static <T> ResponseEntity<BaseResponse> errorResponse(HttpStatus status, String errMsg){
        BaseResponse <T> ar = new BaseResponse<>(status);
        ar.setMessage(errMsg);
        return new ResponseEntity<>(ar,status);
    }
}
