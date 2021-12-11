package com.example.demo_rest.service;

import com.example.demo_rest.model.Role;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public interface RoleService {
    void saveRole(Set<Role> roles);
    Set<Role> getAllRoles();
    Role findRole(Role role);
}
