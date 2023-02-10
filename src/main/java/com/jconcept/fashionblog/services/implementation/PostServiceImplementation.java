package com.jconcept.fashionblog.services.implementation;

import com.jconcept.fashionblog.DTO.request.CommentRequest;
import com.jconcept.fashionblog.DTO.request.LikeRequest;
import com.jconcept.fashionblog.DTO.request.PostRequest;
import com.jconcept.fashionblog.DTO.response.*;
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
import java.time.LocalDateTime;
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
    public CreatePostResponse createPost(PostRequest postRequest) {

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
        return new CreatePostResponse("success" , LocalDateTime.now() , post);
    }

    @Override
    public CommentResponse makeAComment(Long userId, Long postId, CommentRequest commentRequest) {
        User user = findUserById(userId);
        Post post = findPostById(postId);
        if(user != null && post != null) {
            Comment comment = new Comment();
            comment.setComment(commentRequest.getComment());
            comment.setUser(user);
            comment.setPost(post);
            commentRepository.save(comment);
            return new CommentResponse("success", LocalDateTime.now(), comment, post);
        }else {
            throw new PostNotFoundException("User Not Found");
        }
    }

    @Override
    public LikeResponse likeAPost(Long userId, Long postId, LikeRequest likeRequest) {
        Like like = new Like();
        User user = findUserById(userId);
        Post post = findPostById(postId);
        LikeResponse likeResponse = null;
        Like duplicateLike = likeRepository.findLikeByUserIdAndPostId(userId , postId);
        if (duplicateLike == null){
            like.setLiked(likeRequest.isLiked());
            like.setUser(user);
            like.setPost(post);
            likeRepository.save(like);
            List<Like> likeList = likeRepository.findAllLikeByPostId(postId);
            likeResponse = new LikeResponse("success" , LocalDateTime.now() , like , likeList.size());
        }else {
            likeRepository.delete(duplicateLike);
            throw  new PostAlreadyLikedException("This post has been liked, It is now Unliked :(");
        }
        return likeResponse;
    }

    @Override
    public SearchCommentResponse searchComment(String keyword) {
        List<Comment> commentList = commentRepository.findByCommentContaining(keyword);
        return new SearchCommentResponse("success" , LocalDateTime.now() , commentList);
    }

    @Override
    public SearchPostResponse searchPost(String keyword) {
        List<Post> postList = postRepository.findByTitleContainingIgnoreCase(keyword);
        return new SearchPostResponse("success" , LocalDateTime.now() , postList);
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
}
