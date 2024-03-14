package com.ruzzyfer.twitterlike.controller;

import com.ruzzyfer.twitterlike.dto.OnlyId;
import com.ruzzyfer.twitterlike.dto.TweetDto;
import com.ruzzyfer.twitterlike.dto.TweetSaveRequestDto;
import com.ruzzyfer.twitterlike.dto.UserDto;
import com.ruzzyfer.twitterlike.entity.Tweet;
import com.ruzzyfer.twitterlike.entity.User;
import com.ruzzyfer.twitterlike.mapper.TweetMapper;
import com.ruzzyfer.twitterlike.mapper.UserMapper;
import com.ruzzyfer.twitterlike.repository.TweetRepository;
import com.ruzzyfer.twitterlike.repository.UserRepository;
import com.ruzzyfer.twitterlike.service.TweetService;
import jakarta.mail.MessagingException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tweet")
public class TweetController {

    private final TweetRepository tweetRepository;
    private final TweetMapper tweetMapper;
    private final TweetService tweetService;
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public TweetController(TweetRepository tweetRepository, TweetMapper tweetMapper, TweetService tweetService, UserRepository userRepository, UserMapper userMapper) {
        this.tweetRepository = tweetRepository;
        this.tweetMapper = tweetMapper;
        this.tweetService = tweetService;
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    @PostMapping("/save")
    public ResponseEntity<TweetDto> save(@RequestBody TweetSaveRequestDto dto) throws MessagingException {

        TweetDto tweetDto = tweetService.save(dto);

        return new ResponseEntity<>(tweetDto, HttpStatus.OK);
    }

    @PostMapping("/updatetweet")
    public ResponseEntity<String> updateTweet(@RequestBody TweetSaveRequestDto dto) {
        TweetService.UpdateType updateType = tweetService.updateTweet(dto);

        return switch (updateType) {
            case TEXT_CHANGED -> ResponseEntity.status(HttpStatus.OK).body("Tweet text updated successfully.");
            case VIDEO_CHANGED -> ResponseEntity.status(HttpStatus.OK).body("Tweet video URL updated successfully.");
            case TEXT_AND_VIDEO_CHANGED ->
                    ResponseEntity.status(HttpStatus.OK).body("Tweet text and video URL updated successfully.");
            case NOTHING_CHANGED -> ResponseEntity.status(HttpStatus.OK).body("No changes made to the tweet.");
            default -> ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid update type.");
        };
    }

    @GetMapping("/getalltweets")
    public ResponseEntity<List<TweetDto>> getAllTweets() {
        List<TweetDto> tweets = tweetService.getAllTweets();
        return ResponseEntity.status(HttpStatus.OK).body(tweets);
    }

    @GetMapping("/userstweet/{userId}")
    public ResponseEntity<List<TweetDto>> getUserTweets(@PathVariable int userId) {

        UserDto dto = userMapper.toDto(userRepository.findById(userId).orElseThrow());

        List<TweetDto> tweets = tweetService.getUserTweets(dto);
        return new ResponseEntity<>(tweets, HttpStatus.OK);
    }

    @GetMapping("/gettweet/{tweetId}")
    public ResponseEntity<TweetDto> getTweetById(@PathVariable int tweetId) {
        TweetDto tweet = tweetService.getTweetById(tweetId);
        return ResponseEntity.status(HttpStatus.OK).body(tweet);
    }

    @GetMapping("/deletetweet")
    public ResponseEntity<Boolean> getTweetById(@RequestBody OnlyId id) {
        return new ResponseEntity<>(tweetService.deleteTweet(id), HttpStatus.OK);
    }


}
