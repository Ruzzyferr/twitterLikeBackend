package com.ruzzyfer.twitterlike.mapper;

import com.ruzzyfer.twitterlike.dto.ProfileDto;
import com.ruzzyfer.twitterlike.dto.UserDto;
import com.ruzzyfer.twitterlike.dto.UserSaveRequestDto;
import com.ruzzyfer.twitterlike.entity.User;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {

    User toEntity (UserDto dto);

    UserDto toDto (User entity);

    User toEntityFromSaveRequestDto (UserSaveRequestDto dto);

    List<UserDto> toDtoListFromEntity(List<User> all);

    ProfileDto toProfileDto(User user);
}
