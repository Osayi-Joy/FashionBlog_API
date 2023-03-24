package com.jconcept.fashionblog.DTO.request;

import lombok.*;

@Builder
@AllArgsConstructor
@Getter
@Setter
@RequiredArgsConstructor
public class PostDTO {
    private String title ;
    private String description;
    private String featuredImage;
    private Long userId;
}
