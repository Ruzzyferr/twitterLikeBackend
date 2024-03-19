package com.ruzzyfer.twitterlike.repository;

import com.ruzzyfer.twitterlike.entity.Like;
import com.ruzzyfer.twitterlike.entity.Tweet;
import com.ruzzyfer.twitterlike.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LikeRepository extends JpaRepository<Like,Integer> {
    void deleteByUserAndTweet(User user, Tweet tweet);
}
