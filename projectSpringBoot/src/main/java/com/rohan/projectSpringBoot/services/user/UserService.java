package com.rohan.projectSpringBoot.services.user;

import com.rohan.projectSpringBoot.dtos.SignupDTO;
import com.rohan.projectSpringBoot.dtos.UserDTO;

public interface UserService {

	UserDTO createUser(SignupDTO signupDTO);

	boolean hasUserWithEmail(String email);

}
