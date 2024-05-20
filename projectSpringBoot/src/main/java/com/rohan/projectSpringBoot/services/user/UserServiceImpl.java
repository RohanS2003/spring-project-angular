package com.rohan.projectSpringBoot.services.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.rohan.projectSpringBoot.dtos.SignupDTO;
import com.rohan.projectSpringBoot.dtos.UserDTO;
import com.rohan.projectSpringBoot.entities.User;
import com.rohan.projectSpringBoot.repo.UserRepository;

@Service
public class UserServiceImpl implements UserService {
	@Autowired
	private UserRepository repo;
	
	@Override
	public UserDTO createUser(SignupDTO signupDTO) {
		// TODO Auto-generated method stub
		User user=new User();
		user.setEmail(signupDTO.getEmail());
		user.setName(signupDTO.getName());
		user.setPassword(new BCryptPasswordEncoder().encode(signupDTO.getPassword()));
		User createdUser=repo.save(user);
		UserDTO createdUserDTO=new UserDTO();
		createdUserDTO.setId(createdUser.getId());
		return createdUserDTO;
	}

	@Override
	public boolean hasUserWithEmail(String email) {
		// TODO Auto-generated method stub
		return repo.findFirstByEmail(email).isPresent();
	}

}
