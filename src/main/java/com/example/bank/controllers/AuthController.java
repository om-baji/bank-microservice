package com.example.bank.controllers;

import com.example.bank.models.Users;
import com.example.bank.schemas.LoginSchema;
import com.example.bank.schemas.RegisterSchema;
import com.example.bank.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {
    @Autowired
    private UserService service;

    @PostMapping("/register")
    public Users register(@RequestBody RegisterSchema registerSchema) {
        return service.saveUser(registerSchema);
    }

    @PostMapping("/login")
    public String login(@RequestBody LoginSchema loginSchema) {
        String token =  service.loginUser(loginSchema);

        return (token != null)? token : "Something went wrong!";
    }
;}
