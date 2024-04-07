package com.ruzzyfer.twitterlike.repository;

import com.ruzzyfer.twitterlike.dto.UserDto;
import com.ruzzyfer.twitterlike.entity.Follower;
import com.ruzzyfer.twitterlike.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FollowerRepository extends JpaRepository<Follower,Integer> {


    void deleteByFollowerUserAndFollowingUser(User followerUser, User followingUser);
}
