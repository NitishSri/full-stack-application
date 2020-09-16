package com.nitish.application.service;

import com.nitish.application.resourceobject.PostCommentRO;
import com.nitish.application.resourceobject.ThreadComments;

public interface ThreadTopicCommentsService {
	
	public ThreadComments fetchThreadComments(String threadName);
	
	public String postComment(PostCommentRO comment);
	
	public String deleteComment(String threadName, String commentID);
	
	

}
