package com.kosh.authservice.controller;

import com.kosh.authservice.dto.AppUserGetDto;
import com.kosh.authservice.dto.AppUserInsertDto;
import com.kosh.authservice.exception.APIException;
import com.kosh.authservice.model.AuthRequest;
import com.kosh.authservice.model.AuthResponse;
import com.kosh.authservice.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "api/v1/auth")
public class AuthController {

    private final AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping(value = "/login")
    public ResponseEntity<AuthResponse> login(@RequestBody @Valid AuthRequest authRequest, Errors errors) throws APIException {
        if (errors.hasErrors()) {
            throw APIException.forClientError(collectAndFlattenAllErrorMessages(errors));
        }
        return ResponseEntity.ok(authService.login(authRequest));
    }


    @PostMapping(value = "/register")
    public ResponseEntity<AppUserGetDto> login(@RequestBody @Valid AppUserInsertDto appUserInsertDto,Errors errors) throws APIException {
        if (errors.hasErrors()) {
            throw APIException.forClientError(collectAndFlattenAllErrorMessages(errors));
        }
        return ResponseEntity.ok(authService.register(appUserInsertDto));
    }

    public static String collectAndFlattenAllErrorMessages(Errors errors) {
        return errors.getAllErrors().stream().map(error -> error.getDefaultMessage()).collect(Collectors.joining(","));
    }

}
