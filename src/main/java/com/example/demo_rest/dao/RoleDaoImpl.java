package com.example.demo_rest.dao;

import com.example.demo_rest.model.Role;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Repository
public class RoleDaoImpl implements RoleDao{

    @PersistenceContext
    EntityManager entityManager;


    @Override
    public void saveRole(Set<Role> roles) {
        for (Role role: roles) {
            entityManager.persist(role);
        }
    }

    @Override
    public Set<Role> getAllRoles() {
        return entityManager.createQuery("select r from Role r", Role.class).getResultList().stream().collect(Collectors.toSet());
    }

    @Override
    public Role findRole(Role role) {
        return entityManager.createQuery("select  r from Role r where r.name=:name", Role.class)
                .setParameter("name", role.toString())
                .getSingleResult();
    }
}
