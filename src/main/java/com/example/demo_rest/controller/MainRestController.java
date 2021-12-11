package com.example.demo_rest.controller;

import com.example.demo_rest.model.Role;
import com.example.demo_rest.model.User;
import com.example.demo_rest.service.RoleService;
import com.example.demo_rest.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("/admin")
public class MainRestController {

    private final UserService userService;
    private final RoleService roleService;

    @Autowired
    public MainRestController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping(value = "/users")
    public ResponseEntity<List<User>> showAllUsers() {
        List<User> users = userService.getAllUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping(value = "/user")
    public ResponseEntity<User> getAuthentication(Principal principal) {
        User user = userService.getUserByUsername(principal.getName());
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUser(@PathVariable("id") int id) {
        User user = userService.getUserById(id);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<User> createUser(@RequestBody User user) {
        Set<Role> roleEdit = new HashSet<>();
        user.getRoles().forEach((element) -> roleEdit.add(roleService.findRole(element)));
        user.setRoles(roleEdit);
        userService.addUser(user);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable("id") int id, @RequestBody User user) {
        if (user.getRoles().size() != 0) {
            Set<Role> roleEdit = new HashSet<>();
            user.getRoles().forEach((element) -> roleEdit.add(roleService.findRole(element)));
            user.setRoles(roleEdit);
        } else {
            user.setRoles(userService.getUserById(user.getId()).getRoles());
        }
        userService.update(user);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable int id) {
        userService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(value = "/roles")
    public ResponseEntity<Iterable<Role>> getAllRoles() {
        return new ResponseEntity<>(roleService.getAllRoles(), HttpStatus.OK);
    }
}
