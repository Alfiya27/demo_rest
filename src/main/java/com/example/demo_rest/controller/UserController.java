package com.example.demo_rest.controller;

import com.example.demo_rest.model.User;
import com.example.demo_rest.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("/user")
public class UserController {

    private UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(value = "/user")
    public ResponseEntity<User> getAuthenticationUser(Principal principal) {
        User user = userService.getUserByUsername(principal.getName());
        return new ResponseEntity<>(user, HttpStatus.OK);
    }
}



