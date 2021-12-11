//package com.example.demo_rest.service;
//
//import com.example.demo_rest.model.Role;
//import com.example.demo_rest.model.User;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.ArrayList;
//import java.util.HashSet;
//import java.util.List;
//import java.util.Set;
//
//@Service
//public class MyUserDetailsService implements UserDetailsService {
//
//    private UserService userService;
//
//    @Autowired
//    public void setMyUserDetailsService (UserService userService) {
//        this.userService = userService;
//    }
//
//    private List<GrantedAuthority> getAuthorities(Set<Role> userRoles) {
//        Set<GrantedAuthority> roles = new HashSet<>();
//        userRoles.forEach((role) -> {
//            roles.add(new SimpleGrantedAuthority(role.getName()));
//        });
//        List<GrantedAuthority> grantedAuthorities = new ArrayList<>(roles);
//        return grantedAuthorities;
//    }
//
//    @Override
////    @Transactional
////    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
////        User user = userService.findByUsername(userName);
////        user.getAuthorities().size();
////        return user;
////    }
//    public UserDetails loadUserByUsername(String username) {
//        User user = userService.findByUsername(username);
//        List<GrantedAuthority> roleList = getAuthorities(user.getRoles());
//        org.springframework.security.core.userdetails.User userDetails = new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), roleList);
//        return userDetails;
//    }
//}
