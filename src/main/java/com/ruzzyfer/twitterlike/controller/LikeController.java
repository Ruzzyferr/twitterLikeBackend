package com.ruzzyfer.twitterlike.controller;

import com.ruzzyfer.twitterlike.config.JwtService;
import com.ruzzyfer.twitterlike.dto.TweetDto;
import com.ruzzyfer.twitterlike.dto.UserDto;
import com.ruzzyfer.twitterlike.entity.User;
import com.ruzzyfer.twitterlike.mapper.TweetMapper;
import com.ruzzyfer.twitterlike.mapper.UserMapper;
import com.ruzzyfer.twitterlike.repository.TweetRepository;
import com.ruzzyfer.twitterlike.repository.UserRepository;
import com.ruzzyfer.twitterlike.service.LikeService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/likes")
public class LikeController {

    @Autowired
    private LikeService likeService;
    @Autowired
    private JwtService jwtService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private TweetMapper tweetMapper;
    @Autowired
    private TweetRepository tweetRepository;

    @PostMapping("/like/{tweetId}")
    public ResponseEntity<String> likeTweet(@PathVariable int tweetId, HttpServletRequest request) {
        String token = request.getHeader("Authorization");

        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7); // "Bearer " ifadesini çıkartıyoruz

            String username = jwtService.extractUsername(token);
            UserDto user = userMapper.toDto(userRepository.findByUsername(username).orElseThrow());

            TweetDto tweet = tweetMapper.toDto(tweetRepository.findById(tweetId).orElseThrow());

            likeService.likeTweet(user, tweet);

            return new ResponseEntity<>("Tweet Liked Successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Invalid token", HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/unlike/{tweetId}")
    public ResponseEntity<String> unlikeTweet(@PathVariable int tweetId, HttpServletRequest request) {
        String token = request.getHeader("Authorization");

        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7); // "Bearer " ifadesini çıkartıyoruz

            String username = jwtService.extractUsername(token);
            UserDto user = userMapper.toDto(userRepository.findByUsername(username).orElseThrow());

            TweetDto tweet = tweetMapper.toDto(tweetRepository.findById(tweetId).orElseThrow());

            likeService.unlikeTweet(user, tweet);

            return new ResponseEntity<>("Tweet disliked successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Invalid token", HttpStatus.BAD_REQUEST);
        }
    }


}
