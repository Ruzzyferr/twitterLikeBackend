package com.ruzzyfer.twitterlike.dto;

import com.ruzzyfer.twitterlike.enums.Role;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;

@Data
public class UserDto {

    private int id;

    private String username;

    private String email;

    @Enumerated(value = EnumType.STRING)
    private Role role;

}
