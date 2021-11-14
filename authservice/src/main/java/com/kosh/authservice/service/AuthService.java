package com.kosh.authservice.service;

import com.kosh.authservice.dto.AppUserGetDto;
import com.kosh.authservice.dto.AppUserInsertDto;
import com.kosh.authservice.entity.AppUser;
import com.kosh.authservice.exception.APIException;
import com.kosh.authservice.mapper.EntityToDtoMapper;
import com.kosh.authservice.model.AuthRequest;
import com.kosh.authservice.model.AuthResponse;
import com.kosh.authservice.repository.AppUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Date;
import java.util.UUID;

@Service
public class AuthService {

    private final JwtUtil jwt;
    private final EntityToDtoMapper mapper;
    private static final String APP_USER_ROLE = "APP_USER";
    private final AppUserRepository appUserRepository;

    @Autowired
    public AuthService(JwtUtil jwt, EntityToDtoMapper mapper, AppUserRepository appUserRepository) {
        this.jwt = jwt;
        this.mapper = mapper;
        this.appUserRepository = appUserRepository;
    }

    //test user
    private static AppUser getUser() {
        AppUser appUser = new AppUser();
        appUser.setId(UUID.randomUUID().toString());
        appUser.setEmail("test@gmail.com");
        appUser.setRole("ADMIN");
        return appUser;
    }

    public AuthResponse login(AuthRequest authRequest) throws APIException {
        AppUser appUser =appUserRepository.findByEmail(authRequest.getEmail());
        if(appUser==null){
            throw APIException.forClientError("User doesn't exist for the Email");
        }
        if(!BCrypt.checkpw(authRequest.getPassword(),appUser.getPassword())){
            throw APIException.forClientError("Invalid Credentials");
        }
        String accessToken = jwt.generate(appUser, "ACCESS");
        return new AuthResponse(accessToken);
    }

    public AppUserGetDto register(AppUserInsertDto appUserInsertDto) throws APIException {
        if(appUserRepository.existsByEmail(appUserInsertDto.getEmail())){
            throw APIException.forClientError("Email already in use");
        }
        AppUser appUser = mapper.AppUserInsertDtoToEntity(appUserInsertDto);
        appUser.setRole(APP_USER_ROLE);
        appUser.setCreated(new Date());
        appUser.setUpdated(new Date());
        appUser.setId(UUID.randomUUID().toString());
        appUser.setPassword(BCrypt.hashpw(appUserInsertDto.getPassword(), BCrypt.gensalt()));
        appUser = appUserRepository.save(appUser);
        AppUserGetDto appUserGetDto = mapper.AppUserEntityToAppUserGetDto(appUser);
        appUserGetDto.setAccessToken(jwt.generate(appUser, "ACCESS"));
        return appUserGetDto;

    }
}
