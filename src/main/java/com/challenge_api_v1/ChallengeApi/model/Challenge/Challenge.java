package com.challenge_api_v1.ChallengeApi.model.Challenge;

import com.challenge_api_v1.ChallengeApi.dtos.CreateChallengeDto;
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
@Builder
public class Challenge {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String description;

    @Enumerated(EnumType.STRING)
    private CategoryEnum category;

    private int xp;

    @OneToMany(mappedBy = "challenge")
    private List<UserChallenge> userChallenges = new ArrayList<>();


    public Challenge(String title, String description, CategoryEnum category, int xp) {
        this.title = title;
        this.description = description;
        this.category = category;
        this.xp = xp;
    }
}
