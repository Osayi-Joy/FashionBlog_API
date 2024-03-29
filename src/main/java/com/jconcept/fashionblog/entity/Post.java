package com.jconcept.fashionblog.entity;

import com.fasterxml.jackson.annotation.*;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.annotations.UpdateTimestamp;

import java.security.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "posts")
public class Post extends BaseEntity{
    private String title ;
    private String description;
    private String featuredImage;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "userId" , referencedColumnName = "id")
    private  User user;

    @JsonManagedReference
    @OneToMany(mappedBy = "post")
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private List<Comment> commentList = new ArrayList<>();

    @JsonManagedReference
    @OneToMany(mappedBy = "post")
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private  List<Like> likeList = new ArrayList<>();

//    @JsonCreator
//    public Post(@JsonProperty("id") Long id, @JsonProperty("title")String title, @JsonProperty("description")String description, @JsonProperty("featuredImage")String featuredImage) {
//        this.id = id;
//        this.title = title;
//        this.description = description;
//        this.featuredImage = featuredImage;
//    }


}
