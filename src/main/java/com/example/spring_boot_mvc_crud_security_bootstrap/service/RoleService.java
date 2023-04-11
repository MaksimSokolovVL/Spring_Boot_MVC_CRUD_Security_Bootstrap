package com.example.spring_boot_mvc_crud_security_bootstrap.service;


import com.example.spring_boot_mvc_crud_security_bootstrap.model.Role;

import java.util.Set;

public interface RoleService {
    Set<Role> findAllDistinctRoles();

    Role findByRoleName(String roleName);
}

