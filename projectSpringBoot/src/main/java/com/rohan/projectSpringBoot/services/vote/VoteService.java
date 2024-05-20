package com.rohan.projectSpringBoot.services.vote;

import com.rohan.projectSpringBoot.dtos.QuestionVoteDTO;

public interface VoteService {
	public QuestionVoteDTO addVoteToQuestion(QuestionVoteDTO questionVoteDTO);
}
