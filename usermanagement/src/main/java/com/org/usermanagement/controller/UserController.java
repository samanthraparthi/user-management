package com.org.usermanagement.controller;


import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.org.usermanagement.dto.UserRequest;
import com.org.usermanagement.security.CurrentUser;
import com.org.usermanagement.security.UserPrincipal;
import com.org.usermanagement.service.UserService;

@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/user/mydetails")
    @PreAuthorize("hasAnyRole('ROLE_USER','ROLE_ADMIN')") 
    public ResponseEntity<?>  getCurrentUser(@CurrentUser UserPrincipal currentUser) {
        return userService.getCurrentUser(currentUser);
    }
    
    @GetMapping("/user/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?>  getUserById(@PathVariable("id") long id) {
    	return userService.getUserById(id);
    }
    
    @PutMapping("/user/mydetails")
    @PreAuthorize("hasAnyRole('ROLE_USER','ROLE_ADMIN')")
    public ResponseEntity<?> updateMyDetails(@CurrentUser UserPrincipal currentUser, @Valid @RequestBody UserRequest userRequest ) {
    	return userService.updateMyDetails(currentUser,userRequest);
    }
    
    @PutMapping("/user/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> updateUser(@Valid @RequestBody UserRequest userRequest,@PathVariable("id") long id) {
    	return userService.updateUser(userRequest,id);
    }
    
    @DeleteMapping("/user/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> deleteUser(@PathVariable(value = "id") long id) {
    	return userService.deleteUser(id);	
    }

}
