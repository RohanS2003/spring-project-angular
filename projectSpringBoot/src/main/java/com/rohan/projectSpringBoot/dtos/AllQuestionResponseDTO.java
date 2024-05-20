package com.rohan.projectSpringBoot.dtos;

import java.util.List;

import lombok.Data;

@Data
public class AllQuestionResponseDTO {
	private List<QuestionDTO> qDtoList;
	private int totalPages;
	private int pageNumber;
	public List<QuestionDTO> getqDtoList() {
		return qDtoList;
	}
	public void setqDtoList(List<QuestionDTO> qDtoList) {
		this.qDtoList = qDtoList;
	}
	public int getTotalPages() {
		return totalPages;
	}
	public void setTotalPages(int totalPages) {
		this.totalPages = totalPages;
	}
	public int getPageNumber() {
		return pageNumber;
	}
	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}
	
}
