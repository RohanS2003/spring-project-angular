package com.rohan.projectSpringBoot.services.answer;

import com.rohan.projectSpringBoot.dtos.AnswerDTO;

public interface AnswerService {

	AnswerDTO postAnswer(AnswerDTO answerDTO);

	AnswerDTO approveAnswer(Long answerId);

}
