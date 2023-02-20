package com.jconcept.fashionblog.controller;

import com.jconcept.fashionblog.DTO.request.CommentRequest;
import com.jconcept.fashionblog.DTO.request.LikeRequest;
import com.jconcept.fashionblog.DTO.request.PostDTO;
import com.jconcept.fashionblog.DTO.response.*;
import com.jconcept.fashionblog.entity.Post;
import com.jconcept.fashionblog.services.PostService;
import com.jconcept.fashionblog.util.ApiResponseUtil;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RestController
@AllArgsConstructor
@RequestMapping( value = "/api")
public class PostController {
    private final PostService postService;

    @PostMapping(value = "/createPost")
    public ResponseEntity<BaseResponse<PostDTO>> createPost(@RequestBody PostDTO postRequest){
        return ApiResponseUtil.response(CREATED, postService.createPost(postRequest), "New Post");
    }

    @PostMapping(value = "/comment/{userId}/{postId}")
    public ResponseEntity<BaseResponse<String>> comment(@PathVariable(value="userId") Long userId, @PathVariable(value="postId") Long postId, @RequestBody CommentRequest commentRequest){
        URI uri = URI.create(ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/api/comment/{userId}/{postId}")
                .toUriString());
        return ApiResponseUtil.response(CREATED, postService.makeAComment(userId , postId , commentRequest), "Commented");
    }

    @PostMapping(value = "/like/{userId}/{postId}")
    public ResponseEntity<BaseResponse<Integer>> like(@PathVariable(value = "userId") Long userId, @PathVariable(value = "postId") Long postId, @RequestBody LikeRequest likeRequest){
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/like").toUriString());
        return ApiResponseUtil.response(CREATED, postService.likeAPost(userId , postId , likeRequest), "Liked");
    }

    @GetMapping(value = "/searchPost/{keyword}")
    public ResponseEntity<BaseResponse<List<PostDTO>>> searchPostUsingKeyword(@PathVariable(value = "keyword") String keyword){
        return ApiResponseUtil.response(OK, postService.searchPost(keyword), "Found");
    }

    @GetMapping(value = "/searchComment/{keyword}")
    public ResponseEntity<BaseResponse<List<String>>> searchForCommentUsingKeyword(@PathVariable(value = "keyword") String keyword){
        return ApiResponseUtil.response(OK, postService.searchComment(keyword), "Found");
    }

    @GetMapping(value = "/post/{id}")
    public ResponseEntity<BaseResponse<PostDTO>> searchForPostById(@PathVariable(value = "id") Long id){
        return ApiResponseUtil.response(OK, postService.getPostById(id), "Found");
    }

}
