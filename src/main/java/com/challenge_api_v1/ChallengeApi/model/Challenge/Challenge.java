package com.challenge_api_v1.ChallengeApi.model.Challenge;

import com.challenge_api_v1.ChallengeApi.model.UserChallenge.UserChallenge;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity(name = "Challenge")
@Table(name = "challenge")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Challenge {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String description;

    @Enumerated(EnumType.STRING)
    private CategoryEnum category;

    @Enumerated(EnumType.STRING)
    private DifficultyEnum difficulty;

    @OneToMany(mappedBy = "challenge")
    private List<UserChallenge> userChallenges = new ArrayList<>();



}
