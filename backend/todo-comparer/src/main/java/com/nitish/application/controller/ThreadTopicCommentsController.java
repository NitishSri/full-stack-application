package com.nitish.application.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.nitish.application.resourceobject.PostCommentRO;
import com.nitish.application.resourceobject.ThreadComments;
import com.nitish.application.service.ThreadTopicCommentsService;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class ThreadTopicCommentsController {

	@Autowired
	ThreadTopicCommentsService service;

	@GetMapping(path = "/thread/{threadName}", produces = "application/json")
	public ThreadComments fetchThreadComments(@PathVariable String threadName) {
		return service.fetchThreadComments(threadName);
	}

	@PostMapping(path = "/post/comment", consumes = "application/json", produces = "application/json")
	public String postComment(@Valid @RequestBody PostCommentRO comment) {
		return service.postComment(comment);

	}

	@DeleteMapping(path = "/delete/comment/{threadName}/{commentID}")
	public String deleteComment(@PathVariable String threadName, @PathVariable String commentID) {
		return service.deleteComment(threadName, commentID);

	}

	@PutMapping(path = "/like/comment/{author}/{threadName}/{commentID}")
	public String likeComment(@PathVariable String author, @PathVariable String threadName,
			@PathVariable String commentID) {
		return service.likeComment(threadName, commentID, author);

	}

	@PutMapping(path = "/dislike/comment/{author}/{threadName}/{commentID}")
	public String dislikeComment(@PathVariable String author, @PathVariable String threadName,
			@PathVariable String commentID) {
		return service.dislikeComment(threadName, commentID, author);

	}

}
