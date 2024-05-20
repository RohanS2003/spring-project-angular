package com.rohan.projectSpringBoot.services.user.image;

import java.io.IOException;
import java.util.Objects;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.rohan.projectSpringBoot.entities.Answers;
import com.rohan.projectSpringBoot.entities.Image;
import com.rohan.projectSpringBoot.repo.AnswerRepository;
import com.rohan.projectSpringBoot.repo.ImageRepository;

import lombok.RequiredArgsConstructor;

@Service
public class ImageService {
	private final ImageRepository imageRepo;
	private final AnswerRepository ansRepo;

	public ImageService(ImageRepository imageRepo, AnswerRepository ansRepo) {
		super();
		this.imageRepo = imageRepo;
		this.ansRepo = ansRepo;
	}

	public void storeFile(MultipartFile multipartFile, Long answerId) throws IOException {
		Optional<Answers> optionalAnswer = ansRepo.findById(answerId);
		if (optionalAnswer.isPresent()) {
			String fileName = StringUtils.cleanPath(Objects.requireNonNull(multipartFile.getOriginalFilename()));
			Image image = new Image();
			image.setName(fileName);
			image.setAnswer(optionalAnswer.get()); // Accessing optionalAnswer safely
			image.setType(multipartFile.getContentType());
			image.setData(multipartFile.getBytes());
			imageRepo.save(image);
		} else {
			throw new IOException("Answer not found.");
		}
	}

}
