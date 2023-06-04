package com.example.spring_boot_mvc_crud_security_bootstrap.util;

import com.example.spring_boot_mvc_crud_security_bootstrap.model.Role;
import com.example.spring_boot_mvc_crud_security_bootstrap.model.User;

import java.util.ArrayList;
import java.util.List;

public class JoinedRolesUtil {

    public static String joinRoles(User user){
        List<String> roles = new ArrayList<>();
        for (Role role : user.getRoles()) {
            roles.add(role.getRoleName().replace("ROLE_", ""));
        }
        return String.join(", ", roles);
    }
}
