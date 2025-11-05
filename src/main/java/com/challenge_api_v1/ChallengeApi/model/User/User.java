package com.challenge_api_v1.ChallengeApi.model.User;

import com.challenge_api_v1.ChallengeApi.model.UserChallenge.UserChallenge;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity(name = "User")
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    private String email;

    private String password;

    private Integer xp;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UserChallenge> challenges = new ArrayList<>();

}
