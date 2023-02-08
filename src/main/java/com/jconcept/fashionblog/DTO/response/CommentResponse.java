package com.example.blogapplicationrest.response;


import com.example.blogapplicationrest.model.Comment;
import com.example.blogapplicationrest.model.Post;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class CommentResponse {
    private String message;
    private LocalDateTime timeStamp;
    private Comment comment;
    private Post post;

}
