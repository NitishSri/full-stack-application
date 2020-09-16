package com.nitish.application.service.impl;

import static com.mongodb.client.model.Filters.eq;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.nitish.application.entity.ThreadAuthorMapping;
import com.nitish.application.repository.ThreadAuthorMappingRepository;
import com.nitish.application.resourceobject.CommentsRO;
import com.nitish.application.resourceobject.DeleteCommentRO;
import com.nitish.application.resourceobject.PostCommentRO;
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
		List<ThreadAuthorMapping> threadFounds = repository.findBythreadTopicName(threadName);
		ThreadComments threadComments = new ThreadComments();
		MongoCollection<Document> allDocs = mongoTemplate.getCollection(threadName);
		FindIterable<Document> docs = allDocs.find(eq("threadName", threadName));

		List<CommentsRO> allComments = new ArrayList<CommentsRO>();
		Gson gson = new Gson();
		for (Document doc : docs) {
			allComments.add(gson.fromJson(doc.toJson(), CommentsRO.class));
		}

		for (ThreadAuthorMapping thread : threadFounds) {
			threadComments.setThreadAuthorName(thread.getAuthorUsername());
			threadComments.setThreadDisplayName(thread.getDisplayName());
			threadComments.setThreadName(threadName);
			threadComments.setComments(allComments);
			threadComments.setTopicOne(thread.getTopicOne());
			threadComments.setTopicTwo(thread.getTopicTwo());
			break;
		}
		threadComments.setNewThread(false);
		return threadComments;
	}

	@Override
	public String postComment(PostCommentRO comment) {
		MongoCollection<Document> docs = mongoTemplate.getCollection(comment.getThreadName());
		comment.setCommentID(UUID.randomUUID().toString());
		Gson gson = new Gson();
		String jsonDocument = gson.toJson(comment);
		docs.insertOne(Document.parse(jsonDocument));
		return comment.getThreadName();
	}

	@Override
	public String deleteComment(DeleteCommentRO comment) {
		MongoCollection<Document> docs = mongoTemplate.getCollection(comment.getThreadName());
		docs.deleteOne(eq("commentID", comment.getCommendID()));
		return comment.getThreadName();
	}

}
