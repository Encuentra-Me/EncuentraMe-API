package com.research_and_mobile_solutions.encuentra_me.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.research_and_mobile_solutions.encuentra_me.resource.SaveUserRegisterResource;
import com.research_and_mobile_solutions.encuentra_me.resource.SaveUserLoginResource;
import com.research_and_mobile_solutions.encuentra_me.resource.UserLoginResource;
import com.research_and_mobile_solutions.encuentra_me.resource.UserRegisterResource;

import io.swagger.v3.oas.annotations.parameters.RequestBody;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    @PostMapping("/login")
    public UserLoginResource login(@RequestBody SaveUserLoginResource user) {
        System.out.println("Login");
        return new UserLoginResource();
    }
    @PostMapping("/register")
    public UserRegisterResource register(@RequestBody SaveUserRegisterResource user) {
        System.out.println("Register");
        return new UserRegisterResource();
    }

}
