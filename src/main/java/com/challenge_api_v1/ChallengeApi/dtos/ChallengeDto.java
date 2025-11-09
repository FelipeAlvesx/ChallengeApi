package com.challenge_api_v1.ChallengeApi.dtos;

import com.challenge_api_v1.ChallengeApi.model.Challenge.CategoryEnum;
import com.challenge_api_v1.ChallengeApi.model.Challenge.Challenge;

public record ChallengeDto(Long id, String title, String description, CategoryEnum category, int xp) {

    public ChallengeDto(Challenge challenge){
        this(challenge.getId(), challenge.getTitle(), challenge.getDescription(), challenge.getCategory(), challenge.getXp());
    }

}
