package com.rohan.projectSpringBoot.services.questions;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.rohan.projectSpringBoot.dtos.AllQuestionResponseDTO;
import com.rohan.projectSpringBoot.dtos.AnswerDTO;
import com.rohan.projectSpringBoot.dtos.QuestionDTO;
import com.rohan.projectSpringBoot.dtos.SingleQuestionDTO;
import com.rohan.projectSpringBoot.entities.Answers;
import com.rohan.projectSpringBoot.entities.QuestionVote;
import com.rohan.projectSpringBoot.entities.Questions;
import com.rohan.projectSpringBoot.entities.User;
import com.rohan.projectSpringBoot.enums.VoteType;
import com.rohan.projectSpringBoot.repo.AnswerRepository;
import com.rohan.projectSpringBoot.repo.ImageRepository;
import com.rohan.projectSpringBoot.repo.QuestionRepository;
import com.rohan.projectSpringBoot.repo.UserRepository;

@Service
public class QuestionServiceImpl implements QuestionService {
	
	public static final int SEARCH_RESULT_PER_PAGE=5;
	private final UserRepository userRepo;
	private final QuestionRepository qRepo;
	private final AnswerRepository ansRepo;
	private final ImageRepository imageRepo;
	
	public QuestionServiceImpl(UserRepository userRepo, QuestionRepository qRepo,AnswerRepository ansRepo,
			ImageRepository imageRepo) {
		this.userRepo = userRepo;
		this.qRepo = qRepo;
		this.ansRepo = ansRepo;
		this.imageRepo = imageRepo;
	}

	
	@Override
	public QuestionDTO addQuestion(QuestionDTO qDTO) {
		Optional<User> optionalUser = userRepo.findById(qDTO.getUserId());
		if (optionalUser.isPresent()) {
			User user = optionalUser.get();
			Questions q = new Questions();
			q.setTitle(qDTO.getTitle());
			q.setBody(qDTO.getBody());
			q.setTags(qDTO.getTags());
			q.setCreatedDate(new Date());
			q.setUser(user); // Set the user
			Questions createdQuestion = qRepo.save(q);
			QuestionDTO createdQuestionDTO = new QuestionDTO();
			createdQuestionDTO.setId(createdQuestion.getId());
			createdQuestionDTO.setTitle(createdQuestion.getTitle());
			return createdQuestionDTO;
		}
		return null;
	}

	@Override
	public AllQuestionResponseDTO getAllQuestions(int pageNumber) {
		// TODO Auto-generated method stub
		Pageable paging=PageRequest.of(pageNumber,SEARCH_RESULT_PER_PAGE);
		Page<Questions> questionsPage=qRepo.findAll(paging);
		AllQuestionResponseDTO allQuestionResponseDTO=new AllQuestionResponseDTO();
		allQuestionResponseDTO.setqDtoList(questionsPage.getContent().stream().map(Questions::getqDTO).collect(Collectors.toList()));
		allQuestionResponseDTO.setPageNumber(questionsPage.getPageable().getPageNumber());
		allQuestionResponseDTO.setTotalPages(questionsPage.getTotalPages());
		return allQuestionResponseDTO;
	}


	@Override
	public SingleQuestionDTO getQuestionById(Long questionId,Long userId) {
		// TODO Auto-generated method stub
		Optional<Questions> optionalQuestion=qRepo.findById(questionId);
		if(optionalQuestion.isPresent())
		{
			SingleQuestionDTO singleQuestionDTO=new SingleQuestionDTO();
			
			Questions existingQuestion=optionalQuestion.get();
			Optional<QuestionVote> optionalQuestionVote=existingQuestion.getQuestionVoteList().stream().filter(vote->vote.getUser().getId().equals(userId)).findFirst();
			QuestionDTO qDTO=optionalQuestion.get().getqDTO();
			
			qDTO.setVoted(0);
			if(optionalQuestionVote.isPresent())
			{
				if(optionalQuestionVote.get().getVoteType().equals(VoteType.UPVOTE))
				{
					qDTO.setVoted(1);
				}
				else
				{
					qDTO.setVoted(-1);
				}
			}
			
			singleQuestionDTO.setQuestionDTO(qDTO);

			List<AnswerDTO> answerDtoList=new ArrayList<>();
			List<Answers> ansList=ansRepo.findAllByQuestionId(questionId);
			for(Answers answer: ansList)
			{
				AnswerDTO answerDTO=answer.getAnswerDTO();
				answerDTO.setFile(imageRepo.findByAnswer(answer));
				answerDtoList.add(answerDTO);
			}
			singleQuestionDTO.setAnswerDtoList(answerDtoList);
			return singleQuestionDTO;
		}
		return null;
	}


	@Override
	public AllQuestionResponseDTO getQuestionsByUserId(Long userId, int pageNumber) {
		// TODO Auto-generated method stub
		Pageable paging=PageRequest.of(pageNumber,SEARCH_RESULT_PER_PAGE);
		Page<Questions> questionsPage=qRepo.findAllByUserId(userId, paging);
		AllQuestionResponseDTO allQuestionResponseDTO=new AllQuestionResponseDTO();
		allQuestionResponseDTO.setqDtoList(questionsPage.getContent().stream().map(Questions::getqDTO).collect(Collectors.toList()));
		allQuestionResponseDTO.setPageNumber(questionsPage.getPageable().getPageNumber());
		allQuestionResponseDTO.setTotalPages(questionsPage.getTotalPages());
		return allQuestionResponseDTO;
	}

}
