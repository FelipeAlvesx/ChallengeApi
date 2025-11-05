package com.challenge_api_v1.ChallengeApi.dtos;

import com.challenge_api_v1.ChallengeApi.model.User.User;

public record UserDetailsDto(Long id, String username, String email) {

    public UserDetailsDto(User user){
        this(user.getId(), user.getUsername(), user.getEmail());
    }

}
