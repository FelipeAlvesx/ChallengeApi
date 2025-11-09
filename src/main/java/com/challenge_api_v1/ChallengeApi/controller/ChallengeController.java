package com.challenge_api_v1.ChallengeApi.controller;

import com.challenge_api_v1.ChallengeApi.dtos.ChallengeDto;
import com.challenge_api_v1.ChallengeApi.model.User.User;
import com.challenge_api_v1.ChallengeApi.service.ChallengeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/challenge")
public class ChallengeController {

    @Autowired
    private ChallengeService challengeService;

    @GetMapping
    public ResponseEntity<ChallengeDto> todayChallenge(@AuthenticationPrincipal User userDetails){
        Long userId = userDetails.getId();
        var challenge = challengeService.getRandomChallenge(userId);
        return ResponseEntity.ok(challenge);
    }


}
