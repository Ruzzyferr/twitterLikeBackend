package com.ruzzyfer.twitterlike.dto;

import com.ruzzyfer.twitterlike.entity.Tweet;
import com.ruzzyfer.twitterlike.entity.User;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Data
public class LikeDto {

    private int id;

    private User user;

    private Tweet tweet;

}
