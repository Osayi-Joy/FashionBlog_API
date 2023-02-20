package com.jconcept.fashionblog.services;

import com.jconcept.fashionblog.DTO.request.CommentRequest;
import com.jconcept.fashionblog.DTO.request.LikeRequest;
import com.jconcept.fashionblog.DTO.request.PostDTO;
import com.jconcept.fashionblog.DTO.response.*;
import com.jconcept.fashionblog.entity.Comment;
import com.jconcept.fashionblog.entity.Post;

import java.util.List;

public interface PostService {
    PostDTO createPost(PostDTO postRequest);

    String makeAComment(Long userId , Long postId , CommentRequest commentResquest);

    Integer likeAPost(Long userId , Long postId , LikeRequest likeRequest);

    List<String> searchComment(String keyword);


    List<PostDTO> searchPost(String keyword);

    Post findPostById(Long id);
    public PostDTO getPostById(Long id);
    void deleteComment(Long commentId);
}
