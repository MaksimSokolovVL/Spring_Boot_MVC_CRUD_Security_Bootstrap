package com.example.spring_boot_mvc_crud_security_bootstrap.controller;


import com.example.spring_boot_mvc_crud_security_bootstrap.model.User;
import com.example.spring_boot_mvc_crud_security_bootstrap.util.JoinedRolesUtil;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/user")
public class UserController {

    @GetMapping
    public String getUserPage(Model model, Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        model.addAttribute("rolesList", JoinedRolesUtil.joinRoles(user));
        model.addAttribute("userIn", user);
        return "user";
    }
}