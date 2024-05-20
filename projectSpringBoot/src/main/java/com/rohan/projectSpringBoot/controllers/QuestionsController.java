package com.rohan.projectSpringBoot.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rohan.projectSpringBoot.dtos.AllQuestionResponseDTO;
import com.rohan.projectSpringBoot.dtos.QuestionDTO;
import com.rohan.projectSpringBoot.dtos.SingleQuestionDTO;
import com.rohan.projectSpringBoot.services.questions.QuestionService;

@RestController
@RequestMapping("/api")
public class QuestionsController {
	private final QuestionService questionService;

	public QuestionsController(QuestionService questionService) {
		super();
		this.questionService = questionService;
	}
	
	@PostMapping("/question")
	public ResponseEntity<?> postQuestion(@RequestBody QuestionDTO qDTO)
	{
		QuestionDTO createdQuestionDTO=questionService.addQuestion(qDTO);
		if(createdQuestionDTO==null)
		{
			return new ResponseEntity<>("Something went wrong",HttpStatus.BAD_REQUEST);
		}
		return ResponseEntity.status(HttpStatus.CREATED).body(createdQuestionDTO);
	}
	
	@GetMapping("/questions/{pageNumber}")
	public ResponseEntity<AllQuestionResponseDTO> getAllQuestions(@PathVariable int pageNumber)
	{
		AllQuestionResponseDTO allQuestionResponseDTO=questionService.getAllQuestions(pageNumber);
		return ResponseEntity.ok(allQuestionResponseDTO);
	}
	
	@GetMapping("/question/{questionId}/{userId}")
	public ResponseEntity<?> getQuestionById(@PathVariable Long questionId,@PathVariable Long userId)
	{
		SingleQuestionDTO singleQuestionDTO=questionService.getQuestionById(questionId,userId);
		if(singleQuestionDTO==null)
		{
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(singleQuestionDTO);
	}
	
	@GetMapping("/questions/{userId}/{pageNumber}")
	public ResponseEntity<AllQuestionResponseDTO> getQuestionsByUserId(@PathVariable Long userId, @PathVariable int pageNumber)
	{
		AllQuestionResponseDTO allQuestionResponseDTO=questionService.getQuestionsByUserId(userId, pageNumber);
		return ResponseEntity.ok(allQuestionResponseDTO);
	}
}
