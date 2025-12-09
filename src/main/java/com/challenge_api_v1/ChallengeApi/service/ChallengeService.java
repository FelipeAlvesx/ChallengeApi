package com.challenge_api_v1.ChallengeApi.service;

import com.challenge_api_v1.ChallengeApi.dtos.ChallengeDto;
import com.challenge_api_v1.ChallengeApi.dtos.CreateChallengeDto;
import com.challenge_api_v1.ChallengeApi.model.Challenge.Challenge;
import com.challenge_api_v1.ChallengeApi.model.Challenge.ChallengeRepository;
import com.challenge_api_v1.ChallengeApi.model.User.User;
import com.challenge_api_v1.ChallengeApi.model.User.UserRepository;
import com.challenge_api_v1.ChallengeApi.model.UserChallenge.UserChallenge;
import com.challenge_api_v1.ChallengeApi.model.UserChallenge.UserChallengeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;


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
                // Se a challenge de hoje já foi completada, lança uma exceção
                if(todayChallenge.get().getCreatedAt().isEqual(today)){
                    throw new RuntimeException("Challenge de hoje já foi completada");
                }
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
            if(todayChallenge.get().isCompleted()){
                throw new RuntimeException("Challenge de hoje já foi completada");
            }

            UserChallenge userChallenge = todayChallenge.get();
            userChallenge.setCompleted(true);
            userChallenge.getUsers().calculateXp(todayChallenge.get().getChallenge());
            userChallengeRepository.save(userChallenge);
        }

        throw new RuntimeException("Nenhuma challenge encontrada para hoje");

    }

    public Challenge createChallenge(CreateChallengeDto createChallengeDto){
        var challenge = new Challenge(createChallengeDto.title(), createChallengeDto.description(), createChallengeDto.category(), createChallengeDto.xp());
        challengeRepository.save(challenge);
        return challenge;
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



