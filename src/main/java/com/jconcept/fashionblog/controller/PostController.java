package com.jconcept.fashionblog.controller;

import com.jconcept.fashionblog.DTO.request.CommentRequest;
import com.jconcept.fashionblog.DTO.request.LikeRequest;
import com.jconcept.fashionblog.DTO.request.PostRequest;
import com.jconcept.fashionblog.DTO.response.*;
import com.jconcept.fashionblog.entity.Post;
import com.jconcept.fashionblog.services.PostService;
import com.jconcept.fashionblog.services.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RestController
@Slf4j
@AllArgsConstructor
@RequestMapping( value = "/api")
public class PostController {
//    private final UserService userService;
    private final PostService postService;
    @PostMapping(value = "/createPost")
    public ResponseEntity<CreatePostResponse> createPost(@RequestBody PostRequest postRequest){
        log.info("Successfully Created A post With Title:  {} " , postRequest.getTitle());
        return  new ResponseEntity<>(postService.createPost(postRequest) , CREATED);
    }

    @PostMapping(value = "/comment/{userId}/{postId}")
    public ResponseEntity<CommentResponse> comment(@PathVariable(value="userId") Long userId, @PathVariable(value="postId") Long postId, @RequestBody CommentRequest commentRequest){
        log.info("Successfully commented :  {} " , commentRequest.getComment());
        URI uri = URI.create(ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/api/comment/{userId}/{postId}")
                .toUriString());
        return ResponseEntity.created(uri).body(postService.makeAComment(userId , postId , commentRequest));
    }



    @PostMapping(value = "/like/{userId}/{postId}")
    public ResponseEntity<LikeResponse> like(@PathVariable(value = "userId") Long userId, @PathVariable(value = "postId") Long postId, @RequestBody LikeRequest likeRequest){
        log.info("Successfully liked :  {} " , likeRequest.isLiked());
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/like").toUriString());
        return ResponseEntity.created(uri).body(postService.likeAPost(userId , postId , likeRequest));
    }

    @GetMapping(value = "/searchPost/{keyword}")
    public ResponseEntity<SearchPostResponse> searchPost(@PathVariable(value = "keyword") String keyword){
        return new ResponseEntity<>(postService.searchPost(keyword) , OK);
    }

    @GetMapping(value = "/searchComment/{keyword}")
    public ResponseEntity<SearchCommentResponse> searchComment(@PathVariable(value = "keyword") String keyword){
        return ResponseEntity.ok().body(postService.searchComment(keyword));
    }

    @GetMapping(value = "/post/{id}")
    public ResponseEntity<Post> searchComment(@PathVariable(value = "id") Long id){
        return ResponseEntity.ok().body(postService.findPostById(id));
    }

}
