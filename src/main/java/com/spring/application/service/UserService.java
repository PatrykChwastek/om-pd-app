package com.spring.application.service;

import com.spring.application.model.User;
import com.spring.application.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
@Service
@Transactional
public class UserService {
    @Autowired
    private UserRepo userRepo;


    public List<User> getAllUsers() {
        return userRepo.findAll();
    }

    public User getUserById(int id) {
        return userRepo.getUserById(id);
    }
}
