package com.ruzzyfer.twitterlike.mapper;

import com.ruzzyfer.twitterlike.dto.TweetDto;
import com.ruzzyfer.twitterlike.dto.TweetSaveRequestDto;
import com.ruzzyfer.twitterlike.entity.Tweet;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TweetMapper {
    TweetDto toDto (Tweet entity);

    Tweet toEntity (TweetDto dto);

    Tweet toEntityFromSaveRequestDto(TweetSaveRequestDto dto);

    List<TweetDto> toDtoListFromEntityList(List<Tweet> tweets);
}
