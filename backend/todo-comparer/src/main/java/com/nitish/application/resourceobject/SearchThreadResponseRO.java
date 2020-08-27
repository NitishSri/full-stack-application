package com.nitish.application.resourceobject;

public class SearchThreadResponseRO {

	private String threadName;
	private String threadDisplayName;
	private String topicOne;
	private String topicTwo;
	private String authorName;
	private boolean noresult;

	public String getThreadName() {
		return threadName;
	}

	public void setThreadName(String threadName) {
		this.threadName = threadName;
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

	public String getAuthorName() {
		return authorName;
	}

	public void setAuthorName(String authorName) {
		this.authorName = authorName;
	}

	public boolean isNoresult() {
		return noresult;
	}

	public void setNoresult(boolean noresult) {
		this.noresult = noresult;
	}

	public String getThreadDisplayName() {
		return threadDisplayName;
	}

	public void setThreadDisplayName(String threadDisplayName) {
		this.threadDisplayName = threadDisplayName;
	}

}
