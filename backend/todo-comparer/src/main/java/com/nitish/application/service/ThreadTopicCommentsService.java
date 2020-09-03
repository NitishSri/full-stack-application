package com.nitish.application.service;

import com.nitish.application.resourceobject.ThreadComments;

public interface ThreadTopicCommentsService {
	
	public ThreadComments fetchThreadComments(String threadName);
	
	public String postComment(ThreadComments comment);

}
