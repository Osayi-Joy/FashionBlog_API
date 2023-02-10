package com.jconcept.fashionblog.DTO.response;

import com.jconcept.fashionblog.entity.Post;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class SearchPostResponse {

    private String message;
    private LocalDateTime timeStamp;
    private List<Post> posts;
}
