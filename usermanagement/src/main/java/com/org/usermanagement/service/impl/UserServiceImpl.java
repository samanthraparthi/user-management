package com.org.usermanagement.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.org.usermanagement.dto.ApiResponse;
import com.org.usermanagement.dto.UserRequest;
import com.org.usermanagement.exception.AppException;
import com.org.usermanagement.model.User;
import com.org.usermanagement.repository.UserRepository;
import com.org.usermanagement.security.UserPrincipal;
import com.org.usermanagement.service.UserService;

@Service
public class UserServiceImpl implements UserService {
	
	    @Autowired
	    private UserRepository userRepository; 

	    public ResponseEntity<?>  getCurrentUser(UserPrincipal currentUser) {
	    	User u = userRepository.findById(currentUser.getId()).orElseThrow(() -> new AppException("User Not Found"));
	        return new ResponseEntity<>(new ApiResponse<User>(true, u,"user details found successfully"),HttpStatus.OK);
	    }
	    

	    public ResponseEntity<?>  getUserById(long id) {
	    	User u = userRepository.findById(id).orElseThrow(() -> new AppException("User Not Found"));
	        return new ResponseEntity<>(new ApiResponse<User>(true, u,"user details found successfully"),HttpStatus.OK);
	    }
	    
	    
	    public ResponseEntity<?> updateMyDetails(UserPrincipal currentUser, UserRequest userRequest ) {
	    	User u = userRepository.findById(currentUser.getId()).orElseThrow(() -> new AppException("User Not Found"));
	    	u.setName(userRequest.getName());
	    	u.setUsername(userRequest.getUsername());
	    	u = userRepository.save(u);
	    	return new ResponseEntity<>(new ApiResponse<User>(true, u,"user details updated successfully"),HttpStatus.OK);
	    }
	    

	    public ResponseEntity<?> updateUser(UserRequest userRequest,long id) {
	    	User u = userRepository.findById(id).orElseThrow(() -> new AppException("User Not Found"));
	    	u.setName(userRequest.getName());
	    	u.setUsername(userRequest.getUsername());
	    	u = userRepository.save(u);
	    	return new ResponseEntity<>(new ApiResponse<User>(true, u,"user details updated successfully"),HttpStatus.OK);
	    }
	    

	    public ResponseEntity<?> deleteUser(long id) {
	    	User user = userRepository.findById(id).orElseThrow(() -> new AppException("User Not Found"));
	    	userRepository.delete(user);
	    	return new ResponseEntity<>(new ApiResponse<User>(true, "User deleted Successfully"),HttpStatus.OK); 	
	    }

}
