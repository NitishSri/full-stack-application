package com.todoapp.application.service;

import java.util.List;

import com.todoapp.application.dto.TodoRO;
import com.todoapp.application.entity.Todo;

public interface TodoService {
	
	public List<Todo> getAllTodos(String userid);

	public Todo addTodo(TodoRO request);
	
	public Todo updateTodo(TodoRO request);
	
	public String deleteTodo(Long todoId);
	
	public Todo getTodo(Long id);
	
}
