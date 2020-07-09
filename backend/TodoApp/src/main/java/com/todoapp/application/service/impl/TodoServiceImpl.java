package com.todoapp.application.service.impl;

import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.todoapp.application.dto.TodoRO;
import com.todoapp.application.entity.Todo;
import com.todoapp.application.repository.TodoRepository;
import com.todoapp.application.service.TodoService;

@Service
public class TodoServiceImpl implements TodoService {

	@Autowired
	private TodoRepository repository;

	@Override
	public List<Todo> getAllTodos(String userid) {
		return repository.findByUserId(userid);
	}

	@Override
	public Todo addTodo(TodoRO request) {
		ModelMapper modelMapper = new ModelMapper();
		Todo todo = modelMapper.map(request, Todo.class);
		return repository.save(todo);
	}

	@Override
	public Todo updateTodo(TodoRO request) {
		Optional<Todo> foundTodo = repository.findById(request.getId());
		if (foundTodo.isPresent()) {
			Todo todo = foundTodo.get();
			todo.setCompleted(request.isCompleted());
			todo.setDescription(request.getDescription());
			todo.setName(request.getName());
			todo.setTargetDate(request.getTargetDate());
			todo.setUserId(request.getUserId());
			return repository.save(todo);
		}
		return null;
	}

	@Override
	public String deleteTodo(Long todoId) {
		Optional<Todo> todo = repository.findById(todoId);
		if (todo.isPresent()) {
			repository.delete(todo.get());
			return "success";
		} else {
			return "failure";
		}
	}

	@Override
	public Todo getTodo(Long id) {
		Optional<Todo> todo = repository.findById(id);
		if (todo.isPresent()) {
			return todo.get();
		} else {
			return null;
		}
	}

}
