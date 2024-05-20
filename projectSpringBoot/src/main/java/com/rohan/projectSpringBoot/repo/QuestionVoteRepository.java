package com.rohan.projectSpringBoot.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rohan.projectSpringBoot.entities.QuestionVote;

@Repository
public interface QuestionVoteRepository extends JpaRepository<QuestionVote, Long> {

}
