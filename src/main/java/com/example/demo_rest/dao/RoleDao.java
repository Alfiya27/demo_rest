package com.example.demo_rest.dao;

import com.example.demo_rest.model.Role;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public interface RoleDao {
    void saveRole(Set<Role> roles);
    Set<Role> getAllRoles();
    Role findRole(Role role);
}
