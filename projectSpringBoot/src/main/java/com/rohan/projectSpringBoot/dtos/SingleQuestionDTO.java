package com.rohan.projectSpringBoot.dtos;

import java.util.List;

import lombok.Data;

@Data
public class SingleQuestionDTO {
	private QuestionDTO questionDTO;
	private List<AnswerDTO> answerDtoList;

	public QuestionDTO getQuestionDTO() {
		return questionDTO;
	}

	public void setQuestionDTO(QuestionDTO questionDTO) {
		this.questionDTO = questionDTO;
	}

	public List<AnswerDTO> getAnswerDtoList() {
		return answerDtoList;
	}

	public void setAnswerDtoList(List<AnswerDTO> answerDtoList) {
		this.answerDtoList = answerDtoList;
	}
	
}
