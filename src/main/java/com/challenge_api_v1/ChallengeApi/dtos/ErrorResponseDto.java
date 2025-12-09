package com.challenge_api_v1.ChallengeApi.dtos;

public record ErrorResponseDto(String message) {

    public ErrorResponseDto(Exception ex) {
        this(ex.getMessage());

    }

}
