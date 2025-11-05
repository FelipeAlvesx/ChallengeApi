package com.challenge_api_v1.controller;

import com.challenge_api_v1.ChallengeApi.dtos.ChallengeDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/challenge")
public class ChallengeController {


    @GetMapping
    public ResponseEntity<ChallengeDto> todayChallenge(){
        return ResponseEntity.ok().build();
    }


}
