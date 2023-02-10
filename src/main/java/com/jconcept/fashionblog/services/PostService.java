package com.jconcept.fashionblog.services;

import com.jconcept.fashionblog.DTO.request.CommentRequest;
import com.jconcept.fashionblog.DTO.request.LikeRequest;
import com.jconcept.fashionblog.DTO.request.PostRequest;
import com.jconcept.fashionblog.DTO.response.*;
import com.jconcept.fashionblog.entity.Post;

public interface PostService {
    CreatePostResponse createPost(PostRequest postRequest);

    CommentResponse makeAComment(Long userId , Long postId , CommentRequest commentResquest);

    LikeResponse likeAPost(Long userId , Long postId , LikeRequest likeRequest);

    SearchCommentResponse searchComment(String keyword);


    SearchPostResponse searchPost(String keyword);

    Post findPostById(Long id);
    void deleteComment(Long commentId);
}
