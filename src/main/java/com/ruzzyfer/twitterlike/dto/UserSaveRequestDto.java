package com.ruzzyfer.twitterlike.dto;

import com.ruzzyfer.twitterlike.entity.User;
import com.ruzzyfer.twitterlike.enums.Role;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;
import lombok.NonNull;

@Data
public class UserSaveRequestDto {

    private int id;

    @NonNull
    private String username;

    @NonNull
    private String email;

    @NonNull
    private String password;

    @Enumerated(value = EnumType.STRING)
    private Role role = Role.USER;

}
