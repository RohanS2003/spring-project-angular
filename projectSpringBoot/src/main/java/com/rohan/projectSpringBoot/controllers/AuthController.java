package com.rohan.projectSpringBoot.controllers;

import java.io.IOException;
import java.util.Optional;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.rohan.projectSpringBoot.dtos.AuthenticationRequest;
import com.rohan.projectSpringBoot.entities.User;
import com.rohan.projectSpringBoot.repo.UserRepository;
import com.rohan.projectSpringBoot.utils.JwtUtil;

import jakarta.servlet.http.HttpServletResponse;

@RestController
public class AuthController {
	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private UserDetailsService userDetailsService;

	@Autowired
	private JwtUtil jwtUtil;

	@Autowired
	private UserRepository repo;

	public static final String TOKEN_PREFIX = "Bearer ";
	public static final String HEADER_STRING = "Authorization";

	@PostMapping("/authenticate")
	public void createAuthToken(@RequestBody AuthenticationRequest authReq, HttpServletResponse response)
			throws IOException, JSONException {
		try {
			authenticationManager
					.authenticate(new UsernamePasswordAuthenticationToken(authReq.getEmail(), authReq.getPassword()));
		} catch (BadCredentialsException e) {
			throw new BadCredentialsException("Incorrect email or password");
		} catch (DisabledException disabledException) {
			response.sendError(HttpServletResponse.SC_NOT_FOUND, "User not created");
			return;
		}
		final UserDetails userDetails = userDetailsService.loadUserByUsername(authReq.getEmail());

		Optional<User> optionalUser = repo.findFirstByEmail(userDetails.getUsername());

		final String jwt = jwtUtil.generateToken(userDetails.getUsername());

		if (optionalUser.isPresent()) {
			response.getWriter().write(new JSONObject()
					.put("userId", optionalUser.get().getId())
					.toString());

		}

		response.addHeader("Access-Control-Expose-Headers", "Authorization");
		response.setHeader("Access-Control-Allow-Headers",
				"Authorization, X-PINGOTHER, X-Requested-With, Content-Type, Accept, X-Custom-header");
		response.setHeader(HEADER_STRING, TOKEN_PREFIX + jwt);

	}
}
