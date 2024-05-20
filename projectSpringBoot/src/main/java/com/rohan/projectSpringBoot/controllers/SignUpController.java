package com.rohan.projectSpringBoot.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.rohan.projectSpringBoot.dtos.SignupDTO;
import com.rohan.projectSpringBoot.dtos.UserDTO;
import com.rohan.projectSpringBoot.services.user.UserService;

@RestController
public class SignUpController {
	@Autowired
	private UserService userService;

	@PostMapping({ "/sign-up" })
	public ResponseEntity<?> createUser(@RequestBody SignupDTO signupDTO) {
		if (userService.hasUserWithEmail(signupDTO.getEmail()))
			return new ResponseEntity<>("user already exists" + signupDTO.getEmail(), HttpStatus.NOT_ACCEPTABLE);

		UserDTO createdUser = userService.createUser(signupDTO);
		if (createdUser == null)
			return new ResponseEntity<>("user not created", HttpStatus.BAD_REQUEST);

		return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
	}
}
