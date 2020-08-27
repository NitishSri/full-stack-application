package com.nitish.application.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.nitish.application.resourceobject.NewThreadRequestRO;
import com.nitish.application.resourceobject.SearchThreadResponseRO;
import com.nitish.application.resourceobject.ThreadComments;
import com.nitish.application.service.ThreadTopicService;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class ThreadTopicController {
	
	@Autowired
	private ThreadTopicService service;
	
	@GetMapping("/status")
	public String getStatus() {
		return "Todo comparer is running !!";
	}
	
	@PostMapping(path = "/create/thread", consumes = "application/json", produces = "application/json")
	public ThreadComments createCollection(@Valid @RequestBody NewThreadRequestRO threadRequest) {
		return service.createThread(threadRequest);

	}
	
	@PostMapping(path = "/search/thread", consumes = "application/json", produces = "application/json")
	public List<SearchThreadResponseRO> searchCollection(@Valid @RequestBody NewThreadRequestRO threadRequest) {
		return service.searchThread(threadRequest);

	}

}
