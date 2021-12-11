package com.example.demo_rest.service;

import com.example.demo_rest.model.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;
import java.util.Optional;

public interface UserService {
    User getUserById(int id);
    User getUserByUsername(String username);
    UserDetails loadUserByUsername(String username);
    void delete(int id);
    List<User> getAllUsers();
    void addUser(User user);
    void update(User user);

    User findByUsername(String username);
}
