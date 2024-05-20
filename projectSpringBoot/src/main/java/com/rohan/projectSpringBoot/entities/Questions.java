package com.rohan.projectSpringBoot.entities;

import java.util.Date;
import java.util.List;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.rohan.projectSpringBoot.dtos.QuestionDTO;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name="questions")
public class Questions {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	private String title;
	
	@Lob
	@Column(name="body",length=512)
	private String body;
	private Date createdDate;
	private List<String> tags;
	
	private int voteCount=0;
	
	@ManyToOne(fetch=FetchType.LAZY, optional=false)
	@JoinColumn(name="user_id", nullable=false)
	@OnDelete(action=OnDeleteAction.CASCADE)
	@JsonIgnore
	private User user;
	
	@OneToMany(mappedBy="question", cascade=CascadeType.ALL)
	@JsonIgnore
	private List<QuestionVote> questionVoteList;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public List<String> getTags() {
		return tags;
	}

	public void setTags(List<String> tags) {
		this.tags = tags;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	public QuestionDTO getqDTO()
	{
		QuestionDTO qDTO=new QuestionDTO();
		qDTO.setId(id);
		qDTO.setTitle(title);
		qDTO.setBody(body);
		qDTO.setTags(tags);
		qDTO.setCreatedDate(createdDate);
		qDTO.setVoteCount(voteCount);
		qDTO.setUserId(user.getId());
		qDTO.setUsername(user.getName());
		return qDTO;
	}

	public int getVoteCount() {
		return voteCount;
	}

	public void setVoteCount(int voteCount) {
		this.voteCount = voteCount;
	}

	public List<QuestionVote> getQuestionVoteList() {
		return questionVoteList;
	}

	public void setQuestionVoteList(List<QuestionVote> questionVoteList) {
		this.questionVoteList = questionVoteList;
	}
	
}
