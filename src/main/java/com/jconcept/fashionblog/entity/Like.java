package com.jconcept.fashionblog.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;


import java.security.Timestamp;
import java.time.LocalDateTime;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "likes")
public class Like extends BaseEntity{
    private boolean isLiked;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "userId" , referencedColumnName = "id")
    private  User user;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "postId" , referencedColumnName = "id")
    private Post post;

}
