package com.jconcept.fashionblog.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "comments")
public class Comment extends BaseEntity{

    private String comment;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "postId" , referencedColumnName = "id")
    private Post post;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "userId" , referencedColumnName = "id")
    private User user;

}
