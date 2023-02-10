package com.jconcept.fashionblog.DTO.response;

import com.jconcept.fashionblog.entity.Comment;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;


@AllArgsConstructor @Getter @Setter @Builder
public class SearchCommentResponse {

    private String message;
    private LocalDateTime timeStamp;
    private List<Comment> comments;
}
