package com.jconcept.fashionblog.DTO.response;


import com.jconcept.fashionblog.entity.Comment;
import com.jconcept.fashionblog.entity.Post;
import lombok.*;

import java.time.LocalDateTime;

@Builder
@Getter
@Setter
@AllArgsConstructor
public class CommentResponse {
    private String message;
    private LocalDateTime timeStamp;
    private Comment comment;
    private Post post;

}
