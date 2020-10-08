package com.nitish.application.resourceobject;

import java.util.List;

public class ThreadComments {

	private String threadName;
	private String threadAuthorName;
	private boolean newThread;
	private String threadDisplayName;
	private List<CommentsRO> comments;
	private String topicOne;
	private String topicTwo;
	private UserLikedDislikedComments userLikedDislikedComments;

	public String getThreadName() {
		return threadName;
	}

	public void setThreadName(String threadName) {
		this.threadName = threadName;
	}

	public String getThreadAuthorName() {
		return threadAuthorName;
	}

	public void setThreadAuthorName(String threadAuthorName) {
		this.threadAuthorName = threadAuthorName;
	}

	public boolean isNewThread() {
		return newThread;
	}

	public void setNewThread(boolean newThread) {
		this.newThread = newThread;
	}

	public String getThreadDisplayName() {
		return threadDisplayName;
	}

	public void setThreadDisplayName(String threadDisplayName) {
		this.threadDisplayName = threadDisplayName;
	}

	public List<CommentsRO> getComments() {
		return comments;
	}

	public void setComments(List<CommentsRO> comments) {
		this.comments = comments;
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

	public UserLikedDislikedComments getUserLikedDislikedComments() {
		return userLikedDislikedComments;
	}

	public void setUserLikedDislikedComments(UserLikedDislikedComments userLikedDislikedComments) {
		this.userLikedDislikedComments = userLikedDislikedComments;
	}

}
