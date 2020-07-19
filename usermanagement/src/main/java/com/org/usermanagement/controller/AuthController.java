package com.org.usermanagement.controller;



import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.org.usermanagement.dto.LoginRequest;
import com.org.usermanagement.dto.SignUpRequest;
import com.org.usermanagement.service.AuthService;


@RestController
@RequestMapping("/api")
public class AuthController {
	
	@Autowired
	AuthService authService;

    @PostMapping("/auth/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
           return authService.authenticateUser(loginRequest);
    }

    @PostMapping("/auth/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignUpRequest signUpRequest) {
          return authService.registerUser(signUpRequest);
    }
}
