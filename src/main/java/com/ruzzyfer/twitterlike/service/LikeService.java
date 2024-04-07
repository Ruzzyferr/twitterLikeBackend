package com.ruzzyfer.twitterlike.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.ruzzyfer.twitterlike.consumer.DailyUserInteractionConsumer;
import com.ruzzyfer.twitterlike.deSerialazier.UserDeserializer;
import com.ruzzyfer.twitterlike.dto.TweetDto;
import com.ruzzyfer.twitterlike.dto.UserDto;
import com.ruzzyfer.twitterlike.entity.Like;
import com.ruzzyfer.twitterlike.entity.User;
import com.ruzzyfer.twitterlike.entity.UserInteraction;
import com.ruzzyfer.twitterlike.enums.InteractionType;
import com.ruzzyfer.twitterlike.enums.TweetCategory;
import com.ruzzyfer.twitterlike.mapper.LikeMapper;
import com.ruzzyfer.twitterlike.mapper.TweetMapper;
import com.ruzzyfer.twitterlike.mapper.UserMapper;
import com.ruzzyfer.twitterlike.repository.LikeRepository;
import com.ruzzyfer.twitterlike.util.KafkaMessagePublisher;
import org.springframework.stereotype.Service;

@Service
public class LikeService {

    private final LikeRepository likeRepository;
    private final LikeMapper likeMapper;
    private final UserMapper userMapper;
    private final TweetMapper tweetMapper;
    private final DailyUserInteractionConsumer dailyUserInteractionConsumer;

    private final KafkaMessagePublisher publisher;

    public LikeService(LikeRepository likeRepository, LikeMapper likeMapper, UserMapper userMapper, TweetMapper tweetMapper, DailyUserInteractionConsumer dailyUserInteractionConsumer, KafkaMessagePublisher publisher) {
        this.likeRepository = likeRepository;
        this.likeMapper = likeMapper;
        this.userMapper = userMapper;
        this.tweetMapper = tweetMapper;
        this.dailyUserInteractionConsumer = dailyUserInteractionConsumer;
        this.publisher = publisher;
    }

    public void likeTweet(UserDto user, TweetDto tweet) {
        Like like = new Like();
        like.setUser(userMapper.toEntity(user));
        like.setTweet(tweetMapper.toEntity(tweet));
        likeRepository.save(like);
        sendToKafka(userMapper.toEntity(user), tweet.getCategory());
    }

    public void unlikeTweet(UserDto user, TweetDto tweet) {

        likeRepository.deleteByUserAndTweet(userMapper.toEntity(user), tweetMapper.toEntity(tweet));

    }

    private void sendToKafka(User user, TweetCategory category) {
        UserInteraction interaction = new UserInteraction();
        interaction.setUserid(user.getId());
        interaction.setInteractionType(InteractionType.LIKE);
        interaction.setCategory(category);

        ObjectMapper objectMapper = new ObjectMapper();
        SimpleModule module = new SimpleModule();
        module.addDeserializer(User.class, new UserDeserializer());
        objectMapper.registerModule(module);

        String jsonInteraction;
        try {
            jsonInteraction = objectMapper.writeValueAsString(interaction);
        } catch (JsonProcessingException e) {
            // Handle JSON serialization exception
            e.printStackTrace();
            return;
        }
        publisher.sendMessageToTopic("user-interaction-topic", jsonInteraction );

    }


}
