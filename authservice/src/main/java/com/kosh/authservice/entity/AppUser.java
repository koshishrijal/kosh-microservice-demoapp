package com.kosh.authservice.entity;

import lombok.Data;

@Data
public class AppUser {

    private String id;
    private String role;
    private String email;
    private String password;
}
