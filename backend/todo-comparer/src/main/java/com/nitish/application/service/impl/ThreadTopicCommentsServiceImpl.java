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
import com.mongodb.client.model.Updates;
import com.nitish.application.entity.UserCommentActionMapping;
import com.nitish.application.entity.ThreadAuthorMapping;
import com.nitish.application.repository.UserCommentActionMappingRepository;
import com.nitish.application.repository.ThreadAuthorMappingRepository;
import com.nitish.application.resourceobject.CommentsRO;
import com.nitish.application.resourceobject.PostCommentRO;
import com.nitish.application.resourceobject.ThreadComments;
import com.nitish.application.resourceobject.UserLikedDislikedComments;
import com.nitish.application.service.ThreadTopicCommentsService;

@Service
public class ThreadTopicCommentsServiceImpl implements ThreadTopicCommentsService {

	@Autowired
	private MongoTemplate mongoTemplate;

	@Autowired
	private ThreadAuthorMappingRepository threadAuthorMappingRepository;

	@Autowired
	private UserCommentActionMappingRepository userCommentActionMappingRepository;

	@Override
	public ThreadComments fetchThreadComments(String threadName, String loggedInUser) {
		List<ThreadAuthorMapping> threadFounds = threadAuthorMappingRepository.findBythreadTopicName(threadName);
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

		List<UserCommentActionMapping> userActionedComments = userCommentActionMappingRepository
				.findByThreadNameAndUsername(threadName, loggedInUser);
		if (!userActionedComments.isEmpty()) {
			List<String> likedComments = new ArrayList<String>();
			List<String> dislikedComments = new ArrayList<String>();
			for (UserCommentActionMapping userComments : userActionedComments) {
				if ("Like".equalsIgnoreCase(userComments.getCommentAction())) {
					likedComments.add(userComments.getCommentID());
				} else {
					dislikedComments.add(userComments.getCommentID());
				}

			}
			UserLikedDislikedComments userLikedDislikedComments = new UserLikedDislikedComments();
			userLikedDislikedComments.setLikedComments(likedComments);
			userLikedDislikedComments.setDislikedComments(dislikedComments);
			threadComments.setUserLikedDislikedComments(userLikedDislikedComments);
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
		List<ThreadAuthorMapping> threadFounds = threadAuthorMappingRepository
				.findBythreadTopicName(comment.getThreadName());
		for (ThreadAuthorMapping thread : threadFounds) {
			int comments = thread.getTotalComments();
			thread.setTotalComments(comments + 1);
			threadAuthorMappingRepository.save(thread);
			break;
		}

		return comment.getThreadName();
	}

	@Override
	public String deleteComment(String threadName, String commentID) {
		MongoCollection<Document> docs = mongoTemplate.getCollection(threadName);
		docs.deleteOne(eq("commentID", commentID));
		List<ThreadAuthorMapping> threadFounds = threadAuthorMappingRepository.findBythreadTopicName(threadName);
		for (ThreadAuthorMapping thread : threadFounds) {
			int comments = thread.getTotalComments();
			thread.setTotalComments(comments - 1);
			threadAuthorMappingRepository.save(thread);
			break;
		}
		UserCommentActionMapping userCommentActionMapping = userCommentActionMappingRepository
				.findByCommentID(commentID);
		if (null != userCommentActionMapping) {
			userCommentActionMappingRepository.deleteById(userCommentActionMapping.getId());
		}
		return threadName;
	}

	@Override
	public String likeComment(String threadName, String commentID, String author) {
		UserCommentActionMapping userCommentActionMapping = userCommentActionMappingRepository
				.findByCommentIDAndUsername(commentID, author);

		if (null == userCommentActionMapping) {
			MongoCollection<Document> docs = mongoTemplate.getCollection(threadName);
			Document doc = docs.find(eq("commentID", commentID)).first();
			Gson gson = new Gson();
			CommentsRO comment = gson.fromJson(doc.toJson(), CommentsRO.class);
			int like = comment.getCommentLike() + 1;
			docs.updateOne(eq("commentID", commentID), Updates.set("commentLike", like));
			insertDataInUserCommentActionMapping(threadName, commentID, author, "Like");
		} else {
			if ("Dislike".equalsIgnoreCase(userCommentActionMapping.getCommentAction())) {
				increaseLikeCommentDescreaseDislike(threadName, commentID);
				insertDataInUserCommentActionMapping(threadName, commentID, author, "Like");
			}
		}
		return threadName;
	}

	@Override
	public String dislikeComment(String threadName, String commentID, String username) {
		UserCommentActionMapping userCommentActionMapping = userCommentActionMappingRepository
				.findByCommentIDAndUsername(commentID, username);

		if (null == userCommentActionMapping) {
			MongoCollection<Document> docs = mongoTemplate.getCollection(threadName);
			Document doc = docs.find(eq("commentID", commentID)).first();
			Gson gson = new Gson();
			CommentsRO comment = gson.fromJson(doc.toJson(), CommentsRO.class);
			int dislike = comment.getCommentDislike() + 1;
			docs.updateOne(eq("commentID", commentID), Updates.set("commentDislike", dislike));
			insertDataInUserCommentActionMapping(threadName, commentID, username, "Dislike");
		} else {
			if ("Like".equalsIgnoreCase(userCommentActionMapping.getCommentAction())) {
				increaseDisLikeCommentDescreaselike(threadName, commentID);
				insertDataInUserCommentActionMapping(threadName, commentID, username, "Dislike");
			}
		}
		return threadName;
	}

	private void insertDataInUserCommentActionMapping(String threadName, String commentID, String loggedInUserame,
			String action) {
		UserCommentActionMapping userCommentActionMapping = userCommentActionMappingRepository
				.findByCommentIDAndUsername(commentID, loggedInUserame);
		if (null == userCommentActionMapping) {
			userCommentActionMapping = new UserCommentActionMapping();
		}
		userCommentActionMapping.setUsername(loggedInUserame);
		userCommentActionMapping.setCommentAction(action);
		userCommentActionMapping.setCommentID(commentID);
		userCommentActionMapping.setThreadName(threadName);
		userCommentActionMappingRepository.save(userCommentActionMapping);

	}

	public void increaseLikeCommentDescreaseDislike(String threadName, String commentID) {
		MongoCollection<Document> docs = mongoTemplate.getCollection(threadName);
		Document doc = docs.find(eq("commentID", commentID)).first();
		Gson gson = new Gson();
		CommentsRO comment = gson.fromJson(doc.toJson(), CommentsRO.class);
		int like = comment.getCommentLike() + 1;
		docs.updateOne(eq("commentID", commentID), Updates.set("commentLike", like));
		int dislike = comment.getCommentDislike() - 1;
		docs.updateOne(eq("commentID", commentID), Updates.set("commentDislike", dislike));

	}

	public void increaseDisLikeCommentDescreaselike(String threadName, String commentID) {
		MongoCollection<Document> docs = mongoTemplate.getCollection(threadName);
		Document doc = docs.find(eq("commentID", commentID)).first();
		Gson gson = new Gson();
		CommentsRO comment = gson.fromJson(doc.toJson(), CommentsRO.class);
		int like = comment.getCommentLike() - 1;
		docs.updateOne(eq("commentID", commentID), Updates.set("commentLike", like));
		int dislike = comment.getCommentDislike() + 1;
		docs.updateOne(eq("commentID", commentID), Updates.set("commentDislike", dislike));

	}

}
