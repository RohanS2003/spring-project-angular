package com.rohan.projectSpringBoot.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rohan.projectSpringBoot.dtos.AnswerDTO;
import com.rohan.projectSpringBoot.services.answer.AnswerService;

@RestController
@RequestMapping("/api/answer")
public class AnswerController {
	private final AnswerService answerService;

	public AnswerController(AnswerService answerService) {
		super();
		this.answerService = answerService;
	}

	@PostMapping
	public ResponseEntity<?> postAnswer(@RequestBody AnswerDTO answerDTO) {
		AnswerDTO createdAnswerDTO = answerService.postAnswer(answerDTO);
		if (createdAnswerDTO == null) {
			return new ResponseEntity<>("Something went wrong", HttpStatus.BAD_REQUEST);
		}
		return ResponseEntity.status(HttpStatus.CREATED).body(createdAnswerDTO);
	}

	@GetMapping("/{answerId}")
	public ResponseEntity<AnswerDTO> approveAnswer(@PathVariable Long answerId) {
		AnswerDTO approveAnswerDTO = answerService.approveAnswer(answerId);
		if (approveAnswerDTO == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(approveAnswerDTO);

	}
}
