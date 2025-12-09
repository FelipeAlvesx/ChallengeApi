package com.challenge_api_v1.ChallengeApi.controller;

import com.challenge_api_v1.ChallengeApi.dtos.ChallengeDto;
import com.challenge_api_v1.ChallengeApi.dtos.CreateChallengeDto;
import com.challenge_api_v1.ChallengeApi.model.Challenge.Challenge;
import com.challenge_api_v1.ChallengeApi.model.User.User;
import com.challenge_api_v1.ChallengeApi.service.ChallengeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/complete")
    public ResponseEntity<?> completeChallenge(@AuthenticationPrincipal User userDetails){
        Long userId = userDetails.getId();
        challengeService.completeChallenge(userId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/create")
    public ResponseEntity<ChallengeDto> createChallenge(@RequestBody CreateChallengeDto createChallengeDto){
        var challenge = challengeService.createChallenge(createChallengeDto); // criamos o endpoint de criacao de challenges
        return ResponseEntity.ok(new ChallengeDto(challenge));
    }


}
