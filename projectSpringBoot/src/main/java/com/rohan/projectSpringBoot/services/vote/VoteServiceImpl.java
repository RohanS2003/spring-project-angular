package com.rohan.projectSpringBoot.services.vote;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.rohan.projectSpringBoot.dtos.QuestionVoteDTO;
import com.rohan.projectSpringBoot.entities.QuestionVote;
import com.rohan.projectSpringBoot.entities.Questions;
import com.rohan.projectSpringBoot.entities.User;
import com.rohan.projectSpringBoot.enums.VoteType;
import com.rohan.projectSpringBoot.repo.QuestionRepository;
import com.rohan.projectSpringBoot.repo.QuestionVoteRepository;
import com.rohan.projectSpringBoot.repo.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
public class VoteServiceImpl implements VoteService {
	private final QuestionVoteRepository questionVoteRepo;
	private final UserRepository userRepo;
	private final QuestionRepository qRepo;

	public VoteServiceImpl(QuestionVoteRepository questionVoteRepo, UserRepository userRepo, QuestionRepository qRepo) {
		super();
		this.questionVoteRepo = questionVoteRepo;
		this.userRepo = userRepo;
		this.qRepo = qRepo;
	}

	@Override
	public QuestionVoteDTO addVoteToQuestion(QuestionVoteDTO questionVoteDTO) {
		// TODO Auto-generated method stub
		Optional<User> optionalUser = userRepo.findById(questionVoteDTO.getUserId());
		Optional<Questions> optionalQuestion = qRepo.findById(questionVoteDTO.getQuestionId());
		if (optionalUser.isPresent() && optionalQuestion.isPresent()) {
			QuestionVote questionVote = new QuestionVote();
			Questions existingQuestion = optionalQuestion.get();
			questionVote.setVoteType(questionVoteDTO.getVoteType());
			if (questionVote.getVoteType() == VoteType.UPVOTE) {
				existingQuestion.setVoteCount(existingQuestion.getVoteCount() + 1);
			} else {
				existingQuestion.setVoteCount(existingQuestion.getVoteCount() - 1);
			}
			questionVote.setQuestion(optionalQuestion.get());
			questionVote.setUser(optionalUser.get());
			qRepo.save(existingQuestion);
			QuestionVote votedQuestion = questionVoteRepo.save(questionVote);
			QuestionVoteDTO questionVotedDTO = new QuestionVoteDTO();
			questionVotedDTO.setId(votedQuestion.getId());
			return questionVotedDTO;
		}
		return null;
	}

}
