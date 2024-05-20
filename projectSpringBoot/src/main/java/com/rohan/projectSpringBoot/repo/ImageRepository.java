package com.rohan.projectSpringBoot.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rohan.projectSpringBoot.entities.Answers;
import com.rohan.projectSpringBoot.entities.Image;

@Repository
public interface ImageRepository extends JpaRepository<Image, Long>{

	Image findByAnswer(Answers answer);

}
