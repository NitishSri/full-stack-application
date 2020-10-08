package com.nitish.application.resourceobject;

import java.util.List;

public class UserLikedDislikedComments {

	private List<String> likedComments;

	private List<String> dislikedComments;

	public List<String> getLikedComments() {
		return likedComments;
	}

	public void setLikedComments(List<String> likedComments) {
		this.likedComments = likedComments;
	}

	public List<String> getDislikedComments() {
		return dislikedComments;
	}

	public void setDislikedComments(List<String> dislikedComments) {
		this.dislikedComments = dislikedComments;
	}

}
