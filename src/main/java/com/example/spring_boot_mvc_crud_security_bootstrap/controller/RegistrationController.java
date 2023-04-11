package com.example.spring_boot_mvc_crud_security_bootstrap.controller;

import com.example.spring_boot_mvc_crud_security_bootstrap.model.Role;
import com.example.spring_boot_mvc_crud_security_bootstrap.model.User;
import com.example.spring_boot_mvc_crud_security_bootstrap.model.UserRole;
import com.example.spring_boot_mvc_crud_security_bootstrap.repository.RoleRepository;
import com.example.spring_boot_mvc_crud_security_bootstrap.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;


import java.util.Collections;
import java.util.Set;

@Controller
public class RegistrationController {

    private final UserRepository userRepo;
    private final RoleRepository roleRepo;
    private final PasswordEncoder encoder;

    public RegistrationController(UserRepository userRepo, RoleRepository roleRepo, PasswordEncoder encoder) {
        this.userRepo = userRepo;
        this.roleRepo = roleRepo;
        this.encoder = encoder;
    }

    @GetMapping("/registration")
    public String registration() {
        return "registration";
    }

    @PostMapping("/registration")
    public String addUser(User user, Model model) {

        User byUsername = userRepo.findByUsername(user.getUsername());
        Set<Role> allDistinctRoles = roleRepo.findAllDistinctRoles();

        if (byUsername != null) {
            model.addAttribute("user_message", "User exists!");
            return "registration";
        }

        user.setActive(true);

        if (allDistinctRoles.size() == 0) {
            user.setRoles(Collections.singleton(createdUserRole()));
        } else {
            Role byRoleName = roleRepo.findByRoleName(UserRole.USER.getName());
            user.setRoles(Collections.singleton(byRoleName != null ? byRoleName : createdUserRole()));
        }
        user.setPassword(encoder.encode(user.getPassword()));
        userRepo.save(user);
        return "redirect:/login";
    }

    private Role createdUserRole() {
        return new Role(UserRole.USER.getName());
    }
}
