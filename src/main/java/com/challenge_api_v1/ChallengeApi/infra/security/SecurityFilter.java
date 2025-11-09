package com.challenge_api_v1.ChallengeApi.infra.security;

import com.challenge_api_v1.ChallengeApi.model.User.UserRepository;
import com.challenge_api_v1.ChallengeApi.service.TokenService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class SecurityFilter extends OncePerRequestFilter {

    @Autowired
    private TokenService tokenService;

    @Autowired
    private UserRepository userRespository;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        var tokenJWT = getHeaderAuthentication(request);

        if(tokenJWT != null){
            System.out.println("carregando filtro");
            var subject = tokenService.getSubject(tokenJWT);
            var user = userRespository.findByUsername(subject);

            var authentication = new UsernamePasswordAuthenticationToken(user, null , user.getAuthorities());

            SecurityContextHolder.getContext().setAuthentication(authentication);

        }

        filterChain.doFilter(request, response);
    }

    private String getHeaderAuthentication(HttpServletRequest request) {
        var authorization = request.getHeader("Authorization");

        if (authorization != null){
            return authorization.replace("Bearer ", "");
        }

        return null;
    }
}
