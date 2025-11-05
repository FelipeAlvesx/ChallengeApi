package com.challenge_api_v1.ChallengeApi.service;

import com.challenge_api_v1.ChallengeApi.dtos.CreateUserDto;
import com.challenge_api_v1.ChallengeApi.model.User.User;
import com.challenge_api_v1.ChallengeApi.model.User.UserRepository;
import org.springframework.stereotype.Service;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;


@Service
public class LoginService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public void register(CreateUserDto createUserDto){

        // Verificar se email já existe
        if (userRepository.existsByEmail(createUserDto.email())) {
            throw new RuntimeException("email ja existe");
        }

        // Criptografar a senha antes de salvar
        String encryptedPassword = passwordEncoder.encode(createUserDto.password());

        var user = new User(createUserDto.username(), createUserDto.email(), encryptedPassword);
        userRepository.save(user);
    }

}