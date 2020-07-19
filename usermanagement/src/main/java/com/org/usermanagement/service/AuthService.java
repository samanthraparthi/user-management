package com.org.usermanagement.service;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;

import com.org.usermanagement.dto.LoginRequest;
import com.org.usermanagement.dto.SignUpRequest;

public interface AuthService {

	ResponseEntity<?> authenticateUser(@Valid LoginRequest loginRequest);

	ResponseEntity<?> registerUser(@Valid SignUpRequest signUpRequest); 

}
