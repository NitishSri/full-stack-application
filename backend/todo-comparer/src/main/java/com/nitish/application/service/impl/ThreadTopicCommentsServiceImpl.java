package com.nitish.application.service.impl;

import static com.mongodb.client.model.Filters.eq;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.mongodb.client.MongoCollection;
import com.nitish.application.resourceobject.ThreadComments;
import com.nitish.application.service.ThreadTopicCommentsService;

@Service
public class ThreadTopicCommentsServiceImpl implements ThreadTopicCommentsService {

	@Autowired
	private MongoTemplate mongoTemplate;

	@Override
	public ThreadComments fetchThreadComments(String threadName) {
		MongoCollection<Document> allDocs = mongoTemplate.getCollection(threadName);
		Document myDoc = allDocs.find(eq("threadName", "apple_samsung")).first();
		String docJson = myDoc.toJson();
		Gson gson = new Gson();
		ThreadComments threadComments = gson.fromJson(docJson, ThreadComments.class);
		threadComments.getTopicOne().setName(threadComments.getTopicOne().getName().toUpperCase());
		threadComments.getTopicTwo().setName(threadComments.getTopicTwo().getName().toUpperCase());
		return threadComments;
	}

}
