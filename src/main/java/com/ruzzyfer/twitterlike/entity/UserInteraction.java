package com.ruzzyfer.twitterlike.entity;

import com.ruzzyfer.twitterlike.enums.InteractionType;
import com.ruzzyfer.twitterlike.enums.TweetCategory;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Table(name = "user_interaction")
@Data
public class UserInteraction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;


    private int userid;

    @Enumerated(EnumType.STRING)
    private InteractionType interactionType;

    @Enumerated(EnumType.STRING)
    private TweetCategory category;

    private int interactionCount;

    private LocalDate date;

    private int dailyActiveMinutes = 0;
}
