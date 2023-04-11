package com.example.spring_boot_mvc_crud_security_bootstrap.controller;

import com.example.spring_boot_mvc_crud_security_bootstrap.model.User;
import com.example.spring_boot_mvc_crud_security_bootstrap.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;


@RestController
@RequestMapping("/admin")
public class UserRestApiController {

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder encoder;


    //http://localhost:8080/admin/user/1
    @GetMapping("/user/{id}")
    public User getUser(@PathVariable long id) {
        User user = userService.getUserForId(id);

        if (user == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("User with ID %d not found", id));
        }
        return user;
    }

    @PutMapping("/user")
    public User editUser(@RequestBody User upUser) {

        if (userService.getUserForId(upUser.getId()) == null) {
            throw new RuntimeException(String.format("User with ID %d does not exist in database", upUser.getId()));
        }
        if (upUser.getRoles() == null || upUser.getRoles().isEmpty()) {
            throw new RuntimeException("User roles  ->  NULL or EMPTY");
        }

        upUser.setPassword(encoder.encode(upUser.getPassword()));
        userService.saveAndUpdateUser(upUser);
        return upUser;
    }

    @DeleteMapping("/user/{id}")
    public String deleteEmployee(@PathVariable long id) {
        User employee = userService.getUserForId(id);
        if (employee == null) {
            throw new RuntimeException(String.format("User with ID %d does not exist in database", id));
        }
        userService.deleteUser(id);
        return String.format("User with ID = %d was deleted", id);
    }
}
