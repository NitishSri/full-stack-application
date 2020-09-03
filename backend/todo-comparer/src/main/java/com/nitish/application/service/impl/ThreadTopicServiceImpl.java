package com.nitish.application.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import com.nitish.application.entity.ThreadAuthorMapping;
import com.nitish.application.repository.ThreadAuthorMappingRepository;
import com.nitish.application.resourceobject.NewThreadRequestRO;
import com.nitish.application.resourceobject.SearchThreadResponseRO;
import com.nitish.application.resourceobject.ThreadCommentTopicOneRO;
import com.nitish.application.resourceobject.ThreadCommentTopicTwoRO;
import com.nitish.application.resourceobject.ThreadComments;
import com.nitish.application.service.ThreadTopicService;

@Service
public class ThreadTopicServiceImpl implements ThreadTopicService {

	@Autowired
	private ThreadAuthorMappingRepository repository;

	@Autowired
	private MongoTemplate mongoTemplate;

	@Override
	public ThreadComments createThread(NewThreadRequestRO request) {
		ThreadComments response = new ThreadComments();
		String threadName = request.getTopicOne().trim().toLowerCase() + "_"
				+ request.getTopicTwo().trim().toLowerCase();

		ThreadAuthorMapping entity = new ThreadAuthorMapping();
		entity.setAuthorUsername(request.getAuthorUsername());
		entity.setThreadTopicName(threadName);
		entity.setTopicOne(request.getTopicOne());
		entity.setTopicTwo(request.getTopicTwo());
		repository.save(entity);

		mongoTemplate.createCollection(threadName);
		response.setThreadAuthorName(request.getAuthorUsername());
		response.setThreadDisplayName("Comparison between " + request.getTopicOne().trim().toUpperCase() + " and "
				+ request.getTopicTwo().trim().toUpperCase());
		response.setNewThread(true);
		response.setThreadName(threadName);
		ThreadCommentTopicOneRO topicOne = new ThreadCommentTopicOneRO();
		topicOne.setName(request.getTopicOne().toUpperCase());
		topicOne.setComments(null);

		ThreadCommentTopicTwoRO topicTwo = new ThreadCommentTopicTwoRO();
		topicTwo.setName(request.getTopicTwo().toUpperCase());
		topicTwo.setComments(null);

		response.setTopicOne(topicOne);
		response.setTopicTwo(topicTwo);

		response.setTopicOne(topicOne);

		return response;
	}

	@Override
	public List<SearchThreadResponseRO> searchThread(NewThreadRequestRO request) {
		String threadName = request.getTopicOne().trim().toLowerCase() + "_"
				+ request.getTopicTwo().trim().toLowerCase();
		List<SearchThreadResponseRO> listOfThread = new ArrayList<SearchThreadResponseRO>();
		List<ThreadAuthorMapping> threadFounds = repository.findBythreadTopicName(threadName);

		if (null == threadFounds || threadFounds.isEmpty()) {
			return null;
		}

		for (ThreadAuthorMapping thread : threadFounds) {
			SearchThreadResponseRO response = new SearchThreadResponseRO();
			response.setAuthorName(thread.getAuthorUsername());
			response.setNoresult(false);
			response.setThreadDisplayName(
					"Comparing " + thread.getTopicOne().toUpperCase() + " and " + thread.getTopicTwo().toUpperCase());
			response.setThreadName(threadName);
			response.setTopicOne(thread.getTopicOne().toUpperCase());
			response.setTopicTwo(thread.getTopicTwo().toUpperCase());
			listOfThread.add(response);
		}

		return listOfThread;
	}

}
