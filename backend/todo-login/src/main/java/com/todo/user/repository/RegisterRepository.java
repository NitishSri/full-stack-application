package com.todo.user.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.todo.user.entity.UserProfile;

@Repository
public interface RegisterRepository extends CrudRepository<UserProfile, String> {

	public Optional<UserProfile> findByUsername(String username);

}
