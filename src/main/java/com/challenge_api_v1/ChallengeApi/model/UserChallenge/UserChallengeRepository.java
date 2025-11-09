package com.challenge_api_v1.ChallengeApi.model.UserChallenge;

import com.challenge_api_v1.ChallengeApi.model.User.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.Optional;

public interface UserChallengeRepository extends JpaRepository<UserChallenge, Long> {

    @Query("select uc from UserChallenge uc where uc.users = :user AND DATE(uc.createdAt) = :today")
    Optional<UserChallenge> findByUserAndDate(User user, LocalDate today);
}
