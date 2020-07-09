package com.todoapp.application.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.todoapp.application.entity.Todo;

@Repository
public interface TodoRepository extends CrudRepository<Todo, String> {

	public List<Todo> findByUserId(String userid);
	
	public Optional<Todo> findById(Long todoId);

}
