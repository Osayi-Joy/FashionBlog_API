package com.jconcept.fashionblog.DTO.response;

import com.jconcept.fashionblog.entity.Post;
import lombok.*;

import javax.persistence.SecondaryTable;
import java.time.LocalDateTime;

@Builder
@Getter
@Setter
@AllArgsConstructor
public class CreatePostResponse {

    private String message;
    private LocalDateTime timeStamp;
    private Post post;
}
