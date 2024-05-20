package com.rohan.projectSpringBoot.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rohan.projectSpringBoot.dtos.QuestionDTO;
import com.rohan.projectSpringBoot.dtos.QuestionVoteDTO;
import com.rohan.projectSpringBoot.services.vote.VoteService;

@RestController
@RequestMapping("/api")
public class VoteController {
	private final VoteService voteService;

	public VoteController(VoteService voteService) {
		super();
		this.voteService = voteService;
	}

	@PostMapping("/vote")
	public ResponseEntity<?> addVoteToQuestion(@RequestBody QuestionVoteDTO questionVoteDTO) {
		QuestionVoteDTO questionVotedDTO = voteService.addVoteToQuestion(questionVoteDTO);
		if (questionVotedDTO == null) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Something went wrong");
		}
		return ResponseEntity.status(HttpStatus.CREATED).body(questionVotedDTO);
	}
}
