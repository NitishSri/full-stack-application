package com.todoapp.application.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.todoapp.application.dto.TodoRO;
import com.todoapp.application.entity.Todo;
import com.todoapp.application.service.TodoService;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class TodoAppController {

	@Autowired
	private TodoService service;

	@GetMapping("/users/{userid}/todos")
	public ResponseEntity<List<Todo>> getAllTodos(@PathVariable String userid) {
		return ResponseEntity.ok().body(service.getAllTodos(userid));
	}
	
	@GetMapping("/todos/fetch/{id}")
	public ResponseEntity<Todo> getTodo(@PathVariable Long id) {
		return ResponseEntity.ok().body(service.getTodo(id));
	}

	@PostMapping(path = "/todos/add", consumes = "application/json", produces = "application/json")
	public ResponseEntity<Todo> addTodo(@RequestBody TodoRO request) {
		return ResponseEntity.ok().body(service.addTodo(request));
	}
	
	@PutMapping(path = "/todos/update", consumes = "application/json", produces = "application/json")
	public ResponseEntity<Todo> updateTodo(@RequestBody TodoRO request) {
		return ResponseEntity.ok().body(service.updateTodo(request));
	}

	@DeleteMapping("todos/{id}")
	public ResponseEntity<String> deleteTodo(@PathVariable Long id) {
		return ResponseEntity.ok().body(service.deleteTodo(id));
	}

}
