package com.research_and_mobile_solutions.encuentra_me.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class HolaController {

    @GetMapping("/hola")
    public String getHolaMundo(){
        return "Hola mundo";
    }
}

