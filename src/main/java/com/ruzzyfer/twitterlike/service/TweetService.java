package com.ruzzyfer.twitterlike.service;

import com.ruzzyfer.twitterlike.dto.OnlyId;
import com.ruzzyfer.twitterlike.dto.TweetDto;
import com.ruzzyfer.twitterlike.dto.TweetSaveRequestDto;
import com.ruzzyfer.twitterlike.dto.UserDto;
import com.ruzzyfer.twitterlike.entity.Tweet;
import com.ruzzyfer.twitterlike.entity.User;
import com.ruzzyfer.twitterlike.mapper.TweetMapper;
import com.ruzzyfer.twitterlike.repository.TweetRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TweetService {

    private final TweetRepository tweetRepository;
    private final TweetMapper tweetMapper;

    public TweetService(TweetRepository tweetRepository, TweetMapper tweetMapper) {
        this.tweetRepository = tweetRepository;
        this.tweetMapper = tweetMapper;
    }

    public TweetDto save(TweetSaveRequestDto dto) {
        Tweet tweet = new Tweet();

        tweet = tweetMapper.toEntityFromSaveRequestDto(dto);

        tweet.setCreatedAt(LocalDateTime.now());

        return tweetMapper.toDto(tweetRepository.save(tweet));
    }

    //get all tweets
    public List<TweetDto> getAllTweets() {
        return tweetMapper.toDtoListFromEntityList(tweetRepository.findAll());
    }

    //User's tweets
    public List<TweetDto> getUserTweets(UserDto user) {
        return tweetMapper.toDtoListFromEntityList(tweetRepository.findAllByUserId(user.getId()));
    }

    public TweetDto getTweetById(int id) {
        return tweetMapper.toDto(tweetRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tweet not found with id: " + id)));
    }

    public boolean deleteTweet(OnlyId tweetId) {

        Tweet tweet = tweetRepository.findById(tweetId.getId())
                .orElseThrow();

        tweetRepository.delete(tweet);

        return true;
    }

    public enum UpdateType {
        TEXT_CHANGED,
        VIDEO_CHANGED,
        TEXT_AND_VIDEO_CHANGED,
        NOTHING_CHANGED
    }

    public UpdateType updateTweet(TweetSaveRequestDto dto) {
        Tweet tweet = tweetRepository.findById(dto.getId())
                .orElseThrow(() -> new RuntimeException("Tweet not found with id: " + dto.getId()));

        String currentText = tweet.getText();
        String currentVideoUrl = tweet.getVideoUrl();

        if (!currentText.equals(dto.getText()) && !currentVideoUrl.equals(dto.getVideoUrl())) {
            // Açıklama ve video değiştirildi
            tweet.setText(dto.getText());
            tweet.setVideoUrl(dto.getVideoUrl());
            tweetRepository.save(tweet);
            return UpdateType.TEXT_AND_VIDEO_CHANGED;
        } else if (!currentText.equals(dto.getText())) {
            // Sadece açıklama değiştirildi
            tweet.setText(dto.getText());
            tweetRepository.save(tweet);
            return UpdateType.TEXT_CHANGED;
        } else if (!currentVideoUrl.equals(dto.getVideoUrl())) {
            // Sadece video değiştirildi
            tweet.setVideoUrl(dto.getVideoUrl());
            tweetRepository.save(tweet);
            return UpdateType.VIDEO_CHANGED;
        } else {
            // Hiçbir şey değiştirilmedi
            return UpdateType.NOTHING_CHANGED;
        }
    }


}
