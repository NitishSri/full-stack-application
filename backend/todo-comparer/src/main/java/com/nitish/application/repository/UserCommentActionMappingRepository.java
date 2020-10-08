package com.nitish.application.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.nitish.application.entity.UserCommentActionMapping;

public interface UserCommentActionMappingRepository extends CrudRepository<UserCommentActionMapping, Long> {

	UserCommentActionMapping findByCommentIDAndUsername(String commentID, String username);
	
	void deleteByCommentID(String commentID);
	
	UserCommentActionMapping findByCommentID(String commentID);
	
	List<UserCommentActionMapping> findByThreadNameAndUsername(String threadName, String username);
}
