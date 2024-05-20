package com.rohan.projectSpringBoot.services.questions;

import com.rohan.projectSpringBoot.dtos.AllQuestionResponseDTO;
import com.rohan.projectSpringBoot.dtos.QuestionDTO;
import com.rohan.projectSpringBoot.dtos.SingleQuestionDTO;

public interface QuestionService {

	QuestionDTO addQuestion(QuestionDTO qDTO);

	AllQuestionResponseDTO getAllQuestions(int pageNumber);

	AllQuestionResponseDTO getQuestionsByUserId(Long userId, int pageNumber);

	SingleQuestionDTO getQuestionById(Long questionId, Long userId);

}
