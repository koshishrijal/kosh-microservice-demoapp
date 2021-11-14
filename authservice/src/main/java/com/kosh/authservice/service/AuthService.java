package com.kosh.authservice.service;

import com.kosh.authservice.entity.AppUser;
import com.kosh.authservice.model.AuthRequest;
import com.kosh.authservice.model.AuthResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.UUID;

@Service
public class AuthService {

    private final JwtUtil jwt;

    @Autowired
    public AuthService(JwtUtil jwt) {
        this.jwt = jwt;
    }

    private static AppUser getUser() {
        AppUser appUser = new AppUser();
        appUser.setId(UUID.randomUUID().toString());
        appUser.setEmail("test@gmail.com");
        appUser.setRole("ADMIN");
        return appUser;
    }

    public AuthResponse login(AuthRequest authRequest) {

        authRequest.setPassword(BCrypt.hashpw(authRequest.getPassword(), BCrypt.gensalt()));
        AppUser appUser = getUser();
        String accessToken = jwt.generate(appUser, "ACCESS");
        return new AuthResponse(accessToken);
    }
}
