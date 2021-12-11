package com.example.demo_rest.dao;

import com.example.demo_rest.model.Role;
import com.example.demo_rest.model.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Repository
public class UserDaoImpl implements UserDao, UserDetailsService {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void addUser(User user) {
        entityManager.persist(user);
    }

    @Override
    public void update(User user) {
        entityManager.merge(user);
    }

    @Override
    public User findByUsername(String name) {
        return entityManager.createQuery("SELECT u from User u WHERE u.name=:name", User.class)
                .setParameter("name", name).getSingleResult();
    }

    @Override
    public User getUserById(int id) {
        return entityManager.find(User.class, id);

    }

    @Override
    public User getUserByUsername(String name) {
        return entityManager.createQuery("select  u from User u where u.name = :name", User.class)
                .setParameter("name", name)
                .getSingleResult();
    }

    @Override
    public void delete(int id) {
        entityManager.remove(entityManager.find(User.class, id));
    }

    @Override
    public List<User> getAllUsers() {
        return entityManager.createQuery("select  u from User u").getResultList();
    }

    private List<GrantedAuthority> getAuthoritiesEntities(Set<Role> userRoles) {
        Set<GrantedAuthority> roles = new HashSet<>();
        userRoles.forEach((role) -> {
            roles.add(new SimpleGrantedAuthority(role.getName()));
        });
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>(roles);
        return grantedAuthorities;
    }

    @Override
    public UserDetails loadUserByUsername(String name) {
        User user = getUserByUsername(name);
        List<GrantedAuthority> roleList = getAuthoritiesEntities(user.getRoles());
        org.springframework.security.core.userdetails.User userDetails = new org.springframework.security.core.userdetails.User(user.getLogin(), user.getPassword(), roleList);
        return userDetails;
    }

}
