package com.challenge_api_v1.ChallengeApi.model.Challenge;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


public interface ChallengeRepository extends JpaRepository<Challenge, Long> {


    @Query("select c from Challenge c order by rand() limit 1")
    Challenge findRandomChallenge();



}
