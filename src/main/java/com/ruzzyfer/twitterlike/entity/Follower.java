package com.ruzzyfer.twitterlike.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "followers")
@Data
public class Follower {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "following_user_id", nullable = false)
    private User followingUser;

    @ManyToOne
    @JoinColumn(name = "follower_user_id", nullable = false)
    private User followerUser;

}
