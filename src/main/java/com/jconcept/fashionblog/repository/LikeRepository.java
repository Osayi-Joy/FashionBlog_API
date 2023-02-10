package com.jconcept.fashionblog.repository;

import com.jconcept.fashionblog.entity.Like;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LikeRepository extends JpaRepository<Like, Long> {
//    @Query(value = "SELECT  * FROM likes WHERE  user_id = ?1 AND post_id = ?2" , nativeQuery = true)
    Like findLikeByUserIdAndPostId(Long userId , Long postId);

//    @Query(value = "SELECT  * FROM likes WHERE   post_id = ?1" , nativeQuery = true)
    List<Like> findAllLikeByPostId(Long postId);

}
