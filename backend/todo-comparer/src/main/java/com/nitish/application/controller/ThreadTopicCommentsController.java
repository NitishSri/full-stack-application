package com.nitish.application.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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
	public String postComment(@Valid @RequestBody ThreadComments comments) {
		return service.postComment(comments);

	}

}
