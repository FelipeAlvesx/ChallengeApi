package com.challenge_api_v1.ChallengeApi.dtos;

import jakarta.validation.constraints.NotBlank;

public record CreateUserDto(

        @NotBlank
        String username,
        @NotBlank
        String email,
        @NotBlank
        String password


) {
}
