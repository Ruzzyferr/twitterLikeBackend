package com.ruzzyfer.twitterlike.controller;

import com.ruzzyfer.twitterlike.config.JwtService;
import com.ruzzyfer.twitterlike.dto.UserDto;
import com.ruzzyfer.twitterlike.entity.User;
import com.ruzzyfer.twitterlike.mapper.UserMapper;
import com.ruzzyfer.twitterlike.repository.FollowerRepository;
import com.ruzzyfer.twitterlike.repository.UserRepository;
import com.ruzzyfer.twitterlike.service.FollowerService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/followapi")
public class FollowerController {

    @Autowired
    private FollowerService followerService;
    @Autowired
    private FollowerRepository followerRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private JwtService jwtService;


    @PostMapping("/follow/{followingUserId}")
    public ResponseEntity<String> followUser(@PathVariable int followingUserId, HttpServletRequest request) {

        UserDto followingUser = userMapper.toDto(userRepository.findById(followingUserId).orElseThrow());

        UserDto followerUser = userMapper.toDto(checkToken(request));

        followerService.followUser(followerUser, followingUser);
        return ResponseEntity.status(HttpStatus.OK).body("User followed successfully.");
    }

    @DeleteMapping("/unfollow/{followingUserId}")
    public ResponseEntity<String> unfollowUser(@PathVariable int followingUserId, HttpServletRequest request) {
        UserDto followingUser = userMapper.toDto(userRepository.findById(followingUserId).orElseThrow());

        UserDto followerUser = userMapper.toDto(checkToken(request));
        followerService.unfollowUser(followerUser, followingUser);
        return ResponseEntity.status(HttpStatus.OK).body("User unfollowed successfully.");
    }


    public User checkToken(HttpServletRequest request){
        String token = request.getHeader("Authorization");

        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7); // "Bearer " ifadesini çıkartıyoruz

            String username = jwtService.extractUsername(token);
            User user = userRepository.findByUsername(username).orElseThrow();

            if (jwtService.isValid(token,user)) {
                return user;
            } else {
                throw  new RuntimeException("HttpStatus.UNAUTHORIZED");
            }
        } else {
            throw new RuntimeException("HttpStatus.BAD_REQUEST");
        }
    }

}
