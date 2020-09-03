package com.nitish.application.resourceobject;

import java.util.ArrayList;

public class ThreadCommentTopicOneRO {
	private String name;
	private ArrayList<CommentsRO> comments;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ArrayList<CommentsRO> getComments() {
		return comments;
	}

	public void setComments(ArrayList<CommentsRO> comments) {
		this.comments = comments;
	}

}
