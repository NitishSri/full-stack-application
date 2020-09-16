package com.nitish.application.entity;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
public class ThreadAuthorMapping {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long Id;

	private String topicOne;

	private String topicTwo;

	private String threadTopicName;

	private String authorUsername;

	@CreationTimestamp
	private LocalDateTime createdDate;

	@UpdateTimestamp
	private LocalDateTime updatedDate;

	private String displayName;

	private int totalComments;

	public String getThreadTopicName() {
		return threadTopicName;
	}

	public void setThreadTopicName(String threadTopicName) {
		this.threadTopicName = threadTopicName;
	}

	public String getAuthorUsername() {
		return authorUsername;
	}

	public void setAuthorUsername(String authorUsername) {
		this.authorUsername = authorUsername;
	}

	public LocalDateTime getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(LocalDateTime createdDate) {
		this.createdDate = createdDate;
	}

	public LocalDateTime getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(LocalDateTime updatedDate) {
		this.updatedDate = updatedDate;
	}

	public Long getId() {
		return Id;
	}

	public String getTopicOne() {
		return topicOne;
	}

	public void setTopicOne(String topicOne) {
		this.topicOne = topicOne;
	}

	public String getTopicTwo() {
		return topicTwo;
	}

	public void setTopicTwo(String topicTwo) {
		this.topicTwo = topicTwo;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public int getTotalComments() {
		return totalComments;
	}

	public void setTotalComments(int totalComments) {
		this.totalComments = totalComments;
	}

}
