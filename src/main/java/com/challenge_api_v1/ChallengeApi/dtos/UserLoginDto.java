package com.challenge_api_v1.ChallengeApi.dtos;

import jakarta.validation.constraints.NotBlank;

public record UserLoginDto(

        @NotBlank
        String username,

        @NotBlank
        String password) {
}
