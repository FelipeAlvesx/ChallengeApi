package com.challenge_api_v1.ChallengeApi.dtos;

import com.challenge_api_v1.ChallengeApi.model.Challenge.CategoryEnum;

public record CreateChallengeDto(String title, String description, CategoryEnum category, int xp) {
}
