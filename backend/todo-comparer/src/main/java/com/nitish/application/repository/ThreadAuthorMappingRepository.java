package com.nitish.application.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.nitish.application.entity.ThreadAuthorMapping;

@Repository
public interface ThreadAuthorMappingRepository extends CrudRepository<ThreadAuthorMapping, Long> {

	List<ThreadAuthorMapping> findBythreadTopicName(String threadTopcName);
}
