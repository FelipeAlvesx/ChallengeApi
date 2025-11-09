package com.challenge_api_v1.ChallengeApi.controller;

import com.challenge_api_v1.ChallengeApi.dtos.CreateUserDto;
import com.challenge_api_v1.ChallengeApi.dtos.TokenDto;
import com.challenge_api_v1.ChallengeApi.dtos.UserDetailsDto;
import com.challenge_api_v1.ChallengeApi.dtos.UserLoginDto;
import com.challenge_api_v1.ChallengeApi.model.User.User;
import com.challenge_api_v1.ChallengeApi.model.User.UserRepository;
import com.challenge_api_v1.ChallengeApi.service.LoginService;
import com.challenge_api_v1.ChallengeApi.service.TokenService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private LoginService loginService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenService;

    @PostMapping("login")
    public ResponseEntity<?> login(@RequestBody @Valid UserLoginDto userLoginDto){

        var authenticationToken = new UsernamePasswordAuthenticationToken(userLoginDto.username(), userLoginDto.password());
        var autentication = authenticationManager.authenticate(authenticationToken);

        //Devolver Token Jwt na response


        var tokenJwt = tokenService.generateToken((User) autentication.getPrincipal());

        return ResponseEntity.ok(new TokenDto(tokenJwt));
    }



    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody CreateUserDto createUserDto){

        this.loginService.register(createUserDto);

        return ResponseEntity.status(HttpStatus.CREATED).body("CREATED!");
    }

    @GetMapping
    public ResponseEntity<Page<UserDetailsDto>> getAll(@PageableDefault(sort = {"username"}, size = 10) Pageable pageable){
        var pageResponse = userRepository.findAll(pageable).map(UserDetailsDto::new);

        return ResponseEntity.ok(pageResponse);
    }


}
