package com.org.usermanagement.service.impl;

import java.net.URI;
import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.org.usermanagement.dto.ApiResponse;
import com.org.usermanagement.dto.JwtAuthenticationResponse;
import com.org.usermanagement.dto.LoginRequest;
import com.org.usermanagement.dto.SignUpRequest;
import com.org.usermanagement.exception.AppException;
import com.org.usermanagement.model.Role;
import com.org.usermanagement.model.RoleName;
import com.org.usermanagement.model.User;
import com.org.usermanagement.repository.RoleRepository;
import com.org.usermanagement.repository.UserRepository;
import com.org.usermanagement.security.JwtTokenProvider;
import com.org.usermanagement.service.AuthService;

@Service
public class AuthServiceImpl implements AuthService {
	
	@Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;
 
    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    JwtTokenProvider tokenProvider;

    public ResponseEntity<?> authenticateUser(LoginRequest loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsernameOrEmail(),
                        loginRequest.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = tokenProvider.generateToken(authentication);
        return ResponseEntity.ok(new JwtAuthenticationResponse(jwt));
    }

    public ResponseEntity<?> registerUser(SignUpRequest signUpRequest) {
        if(userRepository.existsByUsername(signUpRequest.getUsername())) {
            return new ResponseEntity<>(new ApiResponse<>(false, "Username is already taken!"),
                    HttpStatus.BAD_REQUEST);
        }

        if(userRepository.existsByEmail(signUpRequest.getEmail())) {
            return new ResponseEntity<>(new ApiResponse<>(false, "Email Address already in use!"),
                    HttpStatus.BAD_REQUEST);
        }

        // Creating user's account
        User user = new User(signUpRequest.getName(), signUpRequest.getUsername(),
                signUpRequest.getEmail(), signUpRequest.getPassword());

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        
		if (!signUpRequest.getRoles().isEmpty()) {
			(signUpRequest.getRoles()).forEach(x -> {
				Role userRole = roleRepository.findByName(x).orElseThrow(() -> new AppException("User Role not set."));
				user.addRole(userRole);
			});
		}else {
			Role userRole = roleRepository.findByName(RoleName.ROLE_USER).orElseThrow(() -> new AppException("User Role not set."));
			user.setRoles(Collections.singleton(userRole));
		}

        User result = userRepository.save(user);

        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/users/{username}")
                .buildAndExpand(result.getUsername()).toUri();

        return ResponseEntity.created(location).body(new ApiResponse<User>(true,result,"User registered successfully"));
    }

}
