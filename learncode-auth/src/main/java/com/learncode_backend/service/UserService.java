package com.learncode_backend.service;

import java.util.UUID;

import org.springframework.security.oauth2.jwt.Jwt;

import com.learncode_backend.model.User;

public interface UserService extends ICRUD<User, UUID>{
    User getOrCreateUser(Jwt jwt);
    
}