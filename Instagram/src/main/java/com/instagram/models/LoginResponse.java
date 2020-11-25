package com.instagram.models;

import com.instagram.DTO.CurrentUserProfileDTO;
import com.instagram.DTO.ProfileDTO;

public class LoginResponse {

    private final String accessToken;
    private final String refreshToken;
    private CurrentUserProfileDTO user;


    public LoginResponse(String accessToken, String refreshToken, CurrentUserProfileDTO user) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.user = user;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public CurrentUserProfileDTO getUser(){
        return user;
    }
}
