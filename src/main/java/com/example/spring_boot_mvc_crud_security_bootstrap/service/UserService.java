package com.example.spring_boot_mvc_crud_security_bootstrap.service;


import com.example.spring_boot_mvc_crud_security_bootstrap.model.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {
    List<User> getAllUsers();

    void saveAndUpdateUser(User user);

    User getUserForId(long id);

    void deleteUser(long id);

    User findByUsername(String username);

}

