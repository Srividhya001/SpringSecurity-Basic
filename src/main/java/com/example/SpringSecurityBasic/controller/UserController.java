package com.example.SpringSecurityBasic.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController

public class UserController {

    @GetMapping("/home")
    public String testApi(){
        return "Test API works";
    }

    @GetMapping("/user")
    public String user(){
        System.out.println("Authentication called");
        return "User";

    }

    @GetMapping("/admin")
     public String admin(){
        return "Admin";
    }

}
