package com.nitish.application.resourceobject;

public class ThreadComments {

	private String threadName;
	private String threadAuthorName;
	private ThreadCommentTopicOneRO topicOne;
	private ThreadCommentTopicTwoRO topicTwo;
	private Object id;
	private boolean newThread;
	private String threadDisplayName;

	public String getThreadName() {
		return threadName;
	}

	public Object getId() {
		return id;
	}

	public void setId(Object id) {
		this.id = id;
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

	public ThreadCommentTopicOneRO getTopicOne() {
		return topicOne;
	}

	public void setTopicOne(ThreadCommentTopicOneRO topicOne) {
		this.topicOne = topicOne;
	}

	public ThreadCommentTopicTwoRO getTopicTwo() {
		return topicTwo;
	}

	public void setTopicTwo(ThreadCommentTopicTwoRO topicTwo) {
		this.topicTwo = topicTwo;
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

}
