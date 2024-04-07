package com.ruzzyfer.twitterlike.repository;

import com.ruzzyfer.twitterlike.entity.UserInteraction;
import com.ruzzyfer.twitterlike.enums.InteractionType;
import com.ruzzyfer.twitterlike.enums.TweetCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface UserInteractionRepository extends JpaRepository<UserInteraction,Integer> {


    UserInteraction findByUseridAndCategoryAndInteractionTypeAndDate(
            int user,
            TweetCategory category,
            InteractionType interactionType,
            LocalDate date);
}
