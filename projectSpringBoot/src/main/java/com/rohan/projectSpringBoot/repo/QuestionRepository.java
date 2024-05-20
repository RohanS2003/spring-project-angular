package com.rohan.projectSpringBoot.repo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rohan.projectSpringBoot.entities.Questions;

@Repository
public interface QuestionRepository extends JpaRepository<Questions, Long> {

	Page<Questions> findAllByUserId(Long userId, Pageable paging);

}
