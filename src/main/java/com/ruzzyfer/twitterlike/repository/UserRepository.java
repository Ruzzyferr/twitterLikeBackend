package com.ruzzyfer.twitterlike.repository;

import com.ruzzyfer.twitterlike.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Integer> {

    Optional<User> findByUsername(String username);

    User findByEmail(String email);

    User findByResetToken(String token);
}
