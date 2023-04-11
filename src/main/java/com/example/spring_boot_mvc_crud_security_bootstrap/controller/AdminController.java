package com.example.spring_boot_mvc_crud_security_bootstrap.controller;

import com.example.spring_boot_mvc_crud_security_bootstrap.model.Role;
import com.example.spring_boot_mvc_crud_security_bootstrap.model.User;
import com.example.spring_boot_mvc_crud_security_bootstrap.service.RoleService;
import com.example.spring_boot_mvc_crud_security_bootstrap.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;


@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;

    public AdminController(UserService userService, RoleService roleService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.roleService = roleService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping
    public String adminPanel(Model model, Authentication authentication) {

        User userOnline = (User) authentication.getPrincipal();

        model.addAttribute("rolesList", JoinedRolesUtil.joinRoles(userOnline));
        model.addAttribute("userIn", userOnline);

        List<User> users = userService.getAllUsers();
        model.addAttribute("users", users);

        addNewUser(model);

        return "admin";
    }

    @GetMapping("/addNewUser")
    public String addNewUser(Model model) {

        User tempUser = (User) model.getAttribute("tempUser");
        User user = tempUser == null ? new User() : tempUser;
        Set<Role> allDistinctRoles = roleService.findAllDistinctRoles();
        model.addAttribute("edit_user", user);
        model.addAttribute("allRoles", allDistinctRoles);
        return "add_user";
    }

    @PostMapping("/saveUser")
    public String saveUser(@Valid @ModelAttribute("edit_user") User user,
                           BindingResult bindingResult, Model model) {

        User UserFindByUsername = userService.findByUsername(user.getUsername());
        if (UserFindByUsername != null) {
            model.addAttribute("user_message", "Login exists!");
            model.addAttribute("tempUser", user);
            return addNewUser(model);
        }
        if (bindingResult.hasErrors()) {
            return "redirect:/add_user";
        }
        user.setActive(true);

        if (user.getRoles().size() == 0 || user.getRoles() == null) {
            user.setRoles(Collections.singleton(new Role("ROLE_USER")));
        }
        passwordEncoder(user);
        userService.saveAndUpdateUser(user);
        return "redirect:/admin";
    }


    @PostMapping("/update_user")
    public String updateUser(@ModelAttribute("edit_user") User updateUser, BindingResult result) {

        if (result.hasErrors()) {
            return "admin";
        }
        passwordEncoder(updateUser);
        userService.saveAndUpdateUser(updateUser);
        return "redirect:/admin";
    }

    private void passwordEncoder(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
    }
}
