package com.rohan.projectSpringBoot.services.answer;

import java.util.Date;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.rohan.projectSpringBoot.dtos.AnswerDTO;
import com.rohan.projectSpringBoot.entities.Answers;
import com.rohan.projectSpringBoot.entities.Questions;
import com.rohan.projectSpringBoot.entities.User;
import com.rohan.projectSpringBoot.repo.AnswerRepository;
import com.rohan.projectSpringBoot.repo.QuestionRepository;
import com.rohan.projectSpringBoot.repo.UserRepository;

@Service
public class AnswerServiceImpl implements AnswerService {
	private final UserRepository userRepo;
	private final QuestionRepository qRepo;
	private final AnswerRepository ansRepo;
	
	public AnswerServiceImpl(UserRepository userRepo, QuestionRepository qRepo, AnswerRepository ansRepo) {
		super();
		this.userRepo = userRepo;
		this.qRepo = qRepo;
		this.ansRepo = ansRepo;
	}

	@Override
	public AnswerDTO postAnswer(AnswerDTO answerDTO) {
		// TODO Auto-generated method stub
		Optional<User> optionalUser=userRepo.findById(answerDTO.getUserId());
		Optional<Questions> optionalQuestion=qRepo.findById(answerDTO.getQuestionId());
		if(optionalUser.isPresent() && optionalQuestion.isPresent())
		{
			Answers answer=new Answers();
			answer.setBody(answerDTO.getBody());
			answer.setCreatedDate(new Date());
			answer.setUser(optionalUser.get());
			answer.setQuestion(optionalQuestion.get());
			Answers createdAns=ansRepo.save(answer);
			AnswerDTO createdAnswerDTO=new AnswerDTO();
			createdAnswerDTO.setId(createdAns.getId());
			return createdAnswerDTO;
		}

		return null;
	}
	
	@Override
	public AnswerDTO approveAnswer(Long answerId)
	{
		Optional<Answers> optionalAnswer=ansRepo.findById(answerId);
		if(optionalAnswer.isPresent())
		{
			Answers ans=optionalAnswer.get();
			ans.setApproved(true);
			Answers updatedAnswer=ansRepo.save(ans);
			AnswerDTO updatedAnswerDTO=new AnswerDTO();
			updatedAnswerDTO.setId(updatedAnswer.getId());
			return updatedAnswerDTO;
		}
		return null;
	}

}
