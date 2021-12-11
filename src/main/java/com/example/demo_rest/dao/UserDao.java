package com.example.demo_rest.dao;

import com.example.demo_rest.model.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

public interface UserDao {
    User getUserById(int id);
    User getUserByUsername(String username);
    void delete(int id);
    List<User> getAllUsers();
    UserDetails loadUserByUsername(String name);
    void addUser(User user);
    void update(User user);
    User findByUsername(String username);

}
