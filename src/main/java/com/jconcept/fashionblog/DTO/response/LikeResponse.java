package com.jconcept.fashionblog.DTO.response;

import com.jconcept.fashionblog.entity.Like;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
@Setter
public class LikeResponse {
    private String message;
    private LocalDateTime timeStamp;
    private Like like;
    private int totalLikes;
}
