package com.example.demo_rest.service;

import com.example.demo_rest.model.Role;
import com.example.demo_rest.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class TestUser {
    private final UserService userService;
    private final RoleService roleService;

    @Autowired
    public TestUser(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @PostConstruct
    public void createUser() {

        User user1 = new User();
        user1.setName("admin");
        user1.setLastName("adminov");
        user1.setLogin("admin");
        user1.setEmail("admin@mail.ru");
        user1.setPassword("admin");

        User user2 = new User();
        user2.setName("user");
        user2.setLastName("userov");
        user2.setLogin("user");
        user2.setEmail("userov@mail.ru");
        user2.setPassword("user");

        User user3 = new User();
        user3.setName("dart");
        user3.setLastName("veider");
        user3.setLogin("dart_veider");
        user3.setEmail("luke_i'm_your_father@mail.ru");
        user3.setPassword("42");


        Role role1 = new Role("ROLE_ADMIN");
        Role role2 = new Role("ROLE_USER");
        Set<Role> allRoles = new HashSet<>();
        allRoles.add(role1);
        allRoles.add(role2);
        roleService.saveRole(allRoles);
        user1.setRoles(roleService.getAllRoles());
        userService.addUser(user1);
        user2.setRoles(roleService.getAllRoles().stream().limit(1).collect(Collectors.toSet()));
        userService.addUser(user2);
        user3.setRoles(roleService.getAllRoles().stream().limit(1).collect(Collectors.toSet()));
        userService.addUser(user3);

    }
}
