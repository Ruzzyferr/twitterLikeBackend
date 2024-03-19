package com.ruzzyfer.twitterlike.mapper;

import com.ruzzyfer.twitterlike.dto.LikeDto;
import com.ruzzyfer.twitterlike.dto.LikeSaveRequestDto;
import com.ruzzyfer.twitterlike.dto.TweetDto;
import com.ruzzyfer.twitterlike.dto.TweetSaveRequestDto;
import com.ruzzyfer.twitterlike.entity.Like;
import com.ruzzyfer.twitterlike.entity.Tweet;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface LikeMapper {
    LikeDto toDto (Like entity);

    Like toEntity (LikeDto dto);

    Like toEntityFromSaveRequestDto(LikeSaveRequestDto dto);

    List<LikeDto> toDtoListFromEntityList(List<Like> tweets);
}
