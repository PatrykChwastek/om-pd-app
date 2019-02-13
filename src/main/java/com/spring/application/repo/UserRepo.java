package com.spring.application.repo;

import com.spring.application.model.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UserRepo extends CrudRepository<User, Integer> {
	List<User> findAll();
	User getUserById(int id);
	User findOneByUsername(String username);
}