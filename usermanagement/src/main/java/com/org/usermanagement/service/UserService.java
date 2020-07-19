package com.org.usermanagement.service;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;

import com.org.usermanagement.dto.UserRequest;
import com.org.usermanagement.security.UserPrincipal;

public interface UserService {

	ResponseEntity<?> getCurrentUser(UserPrincipal currentUser);

	ResponseEntity<?> getUserById(long id);

	ResponseEntity<?> updateMyDetails(UserPrincipal currentUser, @Valid UserRequest userRequest);

	ResponseEntity<?> updateUser(@Valid UserRequest userRequest, long id);

	ResponseEntity<?> deleteUser(long id); 

}
