package com.ruzzyfer.twitterlike.repository;

import com.ruzzyfer.twitterlike.dto.TweetDto;
import com.ruzzyfer.twitterlike.entity.Tweet;
import com.ruzzyfer.twitterlike.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TweetRepository extends JpaRepository<Tweet,Integer> {

    List<Tweet> findAllByUserId(int id);
}
