package com.jconcept.fashionblog.services.implementation;

import com.jconcept.fashionblog.DTO.request.CommentRequest;
import com.jconcept.fashionblog.DTO.request.LikeRequest;
import com.jconcept.fashionblog.DTO.request.PostDTO;
import com.jconcept.fashionblog.entity.*;
import com.jconcept.fashionblog.exception.PostAlreadyLikedException;
import com.jconcept.fashionblog.exception.PostNotFoundException;
import com.jconcept.fashionblog.exception.UserNotFoundException;
import com.jconcept.fashionblog.repository.CommentRepository;
import com.jconcept.fashionblog.repository.LikeRepository;
import com.jconcept.fashionblog.repository.PostRepository;
import com.jconcept.fashionblog.repository.UserRepository;
import com.jconcept.fashionblog.services.PostService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.regex.Pattern;

@Service
@AllArgsConstructor
public class PostServiceImplementation implements PostService {
    private final LikeRepository likeRepository;
    private final UserRepository userRepository;
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;

    private static final Pattern NONLATIN = Pattern.compile("[^\\w-]");
    private static final Pattern WHITESPACE = Pattern.compile("[\\s]");


    @Override
    public PostDTO createPost(PostDTO postRequest) {

        Post post = new Post();
        User user = findUserById(postRequest.getUserId());
        if(user == null) {
            throw new UserNotFoundException("User Not Found");
        }
        if(user.getRole().equals(Role.CUSTOMER)){
            throw new UserNotFoundException("NOT Authorised");
        }
        post.setTitle(postRequest.getTitle());
        post.setDescription(postRequest.getDescription());
        post.setFeaturedImage(postRequest.getFeaturedImage());
        post.setUser(user);
        postRepository.save(post);
        return new PostDTO(post.getTitle(), post.getDescription(), post.getFeaturedImage(), user.getId());
    }

    @Override
    public String makeAComment(Long userId, Long postId, CommentRequest commentRequest) {
        User user = findUserById(userId);
        Post post = findPostById(postId);
        if(user != null && post != null) {
            Comment comment = new Comment();
            comment.setComment(commentRequest.getComment());
            comment.setUser(user);
            comment.setPost(post);
            commentRepository.save(comment);
            return String.format("Title: %s Description: %s Featured Image: %s Comment: %s ",
                    post.getTitle(), post.getDescription(), post.getFeaturedImage(), comment.getComment());
        }else {
            throw new PostNotFoundException("User Not Found");
        }
    }

    @Override
    public Integer likeAPost(Long userId, Long postId, LikeRequest likeRequest) {
        Like like = new Like();
        List<Like> likeList;
        User user = findUserById(userId);
        Post post = findPostById(postId);
        Like duplicateLike = likeRepository.findLikeByUserIdAndPostId(userId , postId);
        if (duplicateLike == null){
            like.setLiked(likeRequest.isLiked());
            like.setUser(user);
            like.setPost(post);
            likeRepository.save(like);
            likeList = likeRepository.findAllLikeByPostId(postId);
            return likeList.size();
        }else {
            likeRepository.delete(duplicateLike);
            throw  new PostAlreadyLikedException("This post has been liked, It is now Unliked :(");
        }
    }

    @Override
    public List<String> searchComment(String keyword) {
        List<String> comments = new ArrayList<>();
        for(Comment comment: commentRepository.findByCommentContaining(keyword)){
            comments.add(comment.getComment());
        }
        return comments;
    }

    @Override
    public List<PostDTO> searchPost(String keyword) {
        List<PostDTO> posts = new ArrayList<>();
        for(Post post: postRepository.findByTitleContainingIgnoreCase(keyword)){
            posts.add(new PostDTO(post.getTitle(), post.getDescription(), post.getFeaturedImage(), post.getUser().getId()));
        }
        return posts;
    }

    public User findUserById(Long id){
        return userRepository.findById(id).orElseThrow(()-> new UserNotFoundException("User With ID: " + id + " Not Found "));
    }
    public Post findPostById(Long id){
        return postRepository.findById(id).orElseThrow(()-> new PostNotFoundException("Post With ID: " + id + " Not Found "));
    }

    @Override
    public void deleteComment(Long commentId) {
        if(commentRepository.existsById(commentId)) {
            commentRepository.deleteById(commentId);
        }
    }

    public String makeSlug(String input) {
        String nowhitespace = WHITESPACE.matcher(input).replaceAll("-");
        String normalized = Normalizer.normalize(nowhitespace, Normalizer.Form.NFD);
        String slug = NONLATIN.matcher(normalized).replaceAll("");
        return slug.toLowerCase(Locale.ENGLISH);
    }
    public PostDTO getPostById(Long id){
        Post post = postRepository.findById(id).orElseThrow(()-> new PostNotFoundException("Post With ID: " + id + " Not Found "));
        return new PostDTO(post.getTitle(), post.getDescription(), post.getFeaturedImage(), post.getUser().getId());
    }
}
