package com.ruzzyfer.twitterlike.mapper;

import com.ruzzyfer.twitterlike.dto.UserDto;
import com.ruzzyfer.twitterlike.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface FollowerMapper {


    User toEntity(UserDto followerUser);
}
