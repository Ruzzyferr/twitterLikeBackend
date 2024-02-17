package com.ruzzyfer.twitterlike.dto;

import com.ruzzyfer.twitterlike.enums.Role;
import lombok.Data;

@Data
public class LoginResponse {

    private int id;

    private Role role;

    private String token;

}
