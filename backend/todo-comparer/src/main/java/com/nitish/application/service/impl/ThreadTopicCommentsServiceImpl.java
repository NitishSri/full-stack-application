package com.nitish.application.service.impl;

import static com.mongodb.client.model.Filters.eq;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.mongodb.client.MongoCollection;
import com.nitish.application.entity.ThreadAuthorMapping;
import com.nitish.application.repository.ThreadAuthorMappingRepository;
import com.nitish.application.resourceobject.CommentsRO;
import com.nitish.application.resourceobject.ThreadCommentTopicOneRO;
import com.nitish.application.resourceobject.ThreadCommentTopicTwoRO;
import com.nitish.application.resourceobject.ThreadComments;
import com.nitish.application.service.ThreadTopicCommentsService;

@Service
public class ThreadTopicCommentsServiceImpl implements ThreadTopicCommentsService {

	@Autowired
	private MongoTemplate mongoTemplate;
	
	@Autowired
	private ThreadAuthorMappingRepository repository;

	@Override
	public ThreadComments fetchThreadComments(String threadName) {
		ThreadComments threadComments = null;
		MongoCollection<Document> allDocs = mongoTemplate.getCollection(threadName);
		Document myDoc = allDocs.find(eq("threadName", threadName)).first();
		String topicOneName="";
		String topicTwoName="";
		if(myDoc ==null || myDoc.isEmpty()) {
			List<ThreadAuthorMapping> threadFounds = repository.findBythreadTopicName(threadName);
			threadComments = new ThreadComments();
			for (ThreadAuthorMapping thread : threadFounds) {
				threadComments.setThreadAuthorName(thread.getAuthorUsername());
				threadComments.setThreadName(thread.getThreadTopicName());
				threadComments.setThreadDisplayName(
						"Comparing " + thread.getTopicOne().toUpperCase() + " and " + thread.getTopicTwo().toUpperCase());
				topicOneName = thread.getTopicOne();
				topicTwoName = thread.getTopicTwo();
				break;
			}
			
			
			ThreadCommentTopicOneRO topicOne = new ThreadCommentTopicOneRO();
			topicOne.setName(topicOneName);
			ArrayList<CommentsRO> blankCommentList = new ArrayList<CommentsRO>();
			topicOne.setComments(blankCommentList);
			ThreadCommentTopicTwoRO topicTwo = new ThreadCommentTopicTwoRO();
			topicTwo.setComments(blankCommentList);
			topicTwo.setName(topicTwoName);
			threadComments.setTopicOne(topicOne);
			threadComments.setTopicTwo(topicTwo);
			threadComments.setNewThread(true);
			return threadComments;
		}
		String docJson = myDoc.toJson();
		Gson gson = new Gson();
		threadComments = gson.fromJson(docJson, ThreadComments.class);
		threadComments.getTopicOne().setName(threadComments.getTopicOne().getName().toUpperCase());
		threadComments.getTopicTwo().setName(threadComments.getTopicTwo().getName().toUpperCase());
		threadComments.setNewThread(false);
		return threadComments;
	}

	@Override
	public String postComment(ThreadComments newcomments) {
		MongoCollection<Document> allDocs = mongoTemplate.getCollection(newcomments.getThreadName());
		Document myDoc = allDocs.find(eq("threadName", newcomments.getThreadName())).first();
		Gson gson = new Gson();
		if(myDoc!=null && !myDoc.isEmpty()) {
			String docJson = myDoc.toJson();
			ThreadComments existingComments = gson.fromJson(docJson, ThreadComments.class);
			ThreadCommentTopicOneRO topicOne = existingComments.getTopicOne();
			ArrayList<CommentsRO> commentsTopicOne = topicOne.getComments();
			if(null!=newcomments.getTopicOne().getComments() && newcomments.getTopicOne().getComments().size()>0) {
				commentsTopicOne.add(newcomments.getTopicOne().getComments().get(0));
				topicOne.setComments(commentsTopicOne);
			}
			
			ThreadCommentTopicTwoRO topicTwo = existingComments.getTopicTwo();
			ArrayList<CommentsRO> commentsTopicTwo = topicTwo.getComments();
			if(null!=newcomments.getTopicTwo().getComments() && newcomments.getTopicTwo().getComments().size()>0) {
				commentsTopicTwo.add(newcomments.getTopicTwo().getComments().get(0));
				topicTwo.setComments(commentsTopicTwo);
			}
			existingComments.setTopicOne(topicOne);
			existingComments.setTopicTwo(topicTwo);
			String jsonDocument = gson.toJson(existingComments);
			Document newDoc = Document.parse(jsonDocument);
			allDocs.replaceOne(eq("threadName", newcomments.getThreadName()), newDoc);
			
		}else {
			String jsonDocument = gson.toJson(newcomments);
			allDocs.insertOne(Document.parse(jsonDocument));
		}
		
		return newcomments.getThreadName();
	}

}
