package com.nitish.application.service;

import java.util.List;

import com.nitish.application.resourceobject.NewThreadRequestRO;
import com.nitish.application.resourceobject.SearchThreadResponseRO;
import com.nitish.application.resourceobject.ThreadComments;

public interface ThreadTopicService {

	public ThreadComments createThread(NewThreadRequestRO request);

	public List<SearchThreadResponseRO> searchThread(NewThreadRequestRO request);

}
