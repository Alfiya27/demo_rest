package com.example.demo_rest.service;

import com.example.demo_rest.dao.RoleDao;
import com.example.demo_rest.model.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class RoleServiceImpl implements RoleService {

    private final RoleDao roleDao;

    @Autowired
    public RoleServiceImpl(RoleDao roleDao) {
        this.roleDao = roleDao;
    }

    @Override
    @Transactional
    public void saveRole(Set<Role> roles) {
        roleDao.saveRole(roles);
    }

    @Override
    public Set<Role> getAllRoles() {
        return roleDao.getAllRoles();
    }

    @Override
    @Transactional
    public Role findRole(Role role) {
        return roleDao.findRole(role);
    }

}
