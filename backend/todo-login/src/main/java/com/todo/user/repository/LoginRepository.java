package com.todo.user.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.todo.user.entity.Login;

@Repository
public interface LoginRepository extends CrudRepository<Login, String> {

	public Optional<Login> findByUsername(String username);
}
