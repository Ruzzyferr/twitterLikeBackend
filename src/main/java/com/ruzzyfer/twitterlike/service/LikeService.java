package com.ruzzyfer.twitterlike.service;

import com.ruzzyfer.twitterlike.dto.TweetDto;
import com.ruzzyfer.twitterlike.dto.UserDto;
import com.ruzzyfer.twitterlike.entity.Like;
import com.ruzzyfer.twitterlike.mapper.LikeMapper;
import com.ruzzyfer.twitterlike.mapper.TweetMapper;
import com.ruzzyfer.twitterlike.mapper.UserMapper;
import com.ruzzyfer.twitterlike.repository.LikeRepository;
import org.springframework.stereotype.Service;

@Service
public class LikeService {

    private final LikeRepository likeRepository;
    private final LikeMapper likeMapper;
    private final UserMapper userMapper;
    private final TweetMapper tweetMapper;

    public LikeService(LikeRepository likeRepository, LikeMapper likeMapper, UserMapper userMapper, TweetMapper tweetMapper) {
        this.likeRepository = likeRepository;
        this.likeMapper = likeMapper;
        this.userMapper = userMapper;
        this.tweetMapper = tweetMapper;
    }

    public void likeTweet(UserDto user, TweetDto tweet) {
        Like like = new Like();
        like.setUser(userMapper.toEntity(user));
        like.setTweet(tweetMapper.toEntity(tweet));
        likeRepository.save(like);

    }

    public void unlikeTweet(UserDto user, TweetDto tweet) {

        likeRepository.deleteByUserAndTweet(userMapper.toEntity(user), tweetMapper.toEntity(tweet));

    }


}
