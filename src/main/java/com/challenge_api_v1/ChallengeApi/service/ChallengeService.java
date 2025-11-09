package com.challenge_api_v1.ChallengeApi.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.challenge_api_v1.ChallengeApi.dtos.ChallengeDto;
import com.challenge_api_v1.ChallengeApi.model.Challenge.CategoryEnum;
import com.challenge_api_v1.ChallengeApi.model.Challenge.Challenge;
import com.challenge_api_v1.ChallengeApi.model.Challenge.ChallengeRepository;
import com.challenge_api_v1.ChallengeApi.model.User.User;
import com.challenge_api_v1.ChallengeApi.model.User.UserRepository;
import com.challenge_api_v1.ChallengeApi.model.UserChallenge.UserChallenge;
import com.challenge_api_v1.ChallengeApi.model.UserChallenge.UserChallengeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Random;


@Service
public class ChallengeService {

    @Autowired
    private ChallengeRepository challengeRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserChallengeRepository userChallengeRepository;


    // Fazer uma consulta ao banco de trazer uma Challenge Aleatoria
    public ChallengeDto getRandomChallenge(Long userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        LocalDate today = LocalDate.now();

        // Verifica se o usuário já tem uma challenge do dia
        Optional<UserChallenge> todayChallenge = userChallengeRepository.findByUserAndDate(user, today);

        if(todayChallenge.isPresent()){

            if(todayChallenge.get().isCompleted()){
                // Gera uma nova challenge se a de hoje já foi completada
                var randomChallenge = createRandomChallenge(user);
                return new ChallengeDto(randomChallenge);
            }

            // Retorna a mesma challenge se ainda for válida
            return new ChallengeDto(todayChallenge.get().getChallenge());
        }

        // Caso contrário, gera uma aleatória
        var randomChallenge = createRandomChallenge(user);
        return new ChallengeDto(randomChallenge);

    }

    public void completeChallenge(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        LocalDate today = LocalDate.now();

        // Verifica se o usuário já tem uma challenge do dia
        Optional<UserChallenge> todayChallenge = userChallengeRepository.findByUserAndDate(user, today);

        if (todayChallenge.isPresent()) {
            UserChallenge userChallenge = todayChallenge.get();
            userChallenge.setCompleted(true);
            userChallengeRepository.save(userChallenge);
        } else {
            throw new RuntimeException("Nenhuma challenge encontrada para hoje");
        }
    }


    public Challenge createRandomChallenge(User user){

        var randomChallenge = challengeRepository.findRandomChallenge();

        UserChallenge newUserChallenge = new UserChallenge();
        newUserChallenge.setUsers(user);
        newUserChallenge.setChallenge(randomChallenge);
        newUserChallenge.setCompleted(false);
        newUserChallenge.setCreatedAt(LocalDate.now());

        userChallengeRepository.save(newUserChallenge);

        return randomChallenge;

    }


}



