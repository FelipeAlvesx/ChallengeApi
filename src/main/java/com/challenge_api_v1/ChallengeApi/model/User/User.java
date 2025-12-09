package com.challenge_api_v1.ChallengeApi.model.User;

import com.challenge_api_v1.ChallengeApi.dtos.CreateUserDto;
import com.challenge_api_v1.ChallengeApi.model.Challenge.Challenge;
import com.challenge_api_v1.ChallengeApi.model.UserChallenge.UserChallenge;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Entity(name = "User")
@Table(name = "users")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    @Column(unique = true)
    private String email;

    private String password;

    @Column(name = "types")
    @Enumerated(EnumType.STRING)
    private UserType userType;

    private int xp;

    @OneToMany(mappedBy = "users", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UserChallenge> challenges = new ArrayList<>();


    public User(CreateUserDto createUserDto){
        this(createUserDto.username(), createUserDto.email(), createUserDto.password());

    }
    public User(String username, String email, String password){
        this.username = username;
        this.email = email;
        this.password = password;
        this.userType = UserType.USER;
    }


    public void getAdmin(){
        this.userType = UserType.ADMIN;
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(new SimpleGrantedAuthority("ROLE_" + userType.name())); // forma de definir um tipo de usuario, perfil ou role
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return UserDetails.super.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return UserDetails.super.isAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return UserDetails.super.isCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return UserDetails.super.isEnabled();
    }

    public void calculateXp(Challenge challenge){
        this.xp += challenge.getXp();
    }

}
