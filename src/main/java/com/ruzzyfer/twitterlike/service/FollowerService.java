package com.ruzzyfer.twitterlike.service;

import com.ruzzyfer.twitterlike.dto.UserDto;
import com.ruzzyfer.twitterlike.entity.Follower;
import com.ruzzyfer.twitterlike.mapper.FollowerMapper;
import com.ruzzyfer.twitterlike.repository.FollowerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FollowerService {

    @Autowired
    private FollowerRepository followerRepository;
    @Autowired
    private FollowerMapper followerMapper;

    public void followUser(UserDto followerUser, UserDto followingUser) {
        Follower follower = new Follower();
        follower.setFollowerUser(followerMapper.toEntity(followerUser));
        follower.setFollowingUser(followerMapper.toEntity(followingUser));
        followerRepository.save(follower);
    }

    public void unfollowUser(UserDto followerUser, UserDto followingUser) {
        followerRepository.deleteByFollowerUserAndFollowingUser(
                followerMapper.toEntity(followerUser),
                followerMapper.toEntity(followingUser));
    }

}
