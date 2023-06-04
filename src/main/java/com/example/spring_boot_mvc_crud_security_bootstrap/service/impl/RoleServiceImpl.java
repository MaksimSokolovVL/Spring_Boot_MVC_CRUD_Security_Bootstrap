package com.example.spring_boot_mvc_crud_security_bootstrap.service.impl;


import com.example.spring_boot_mvc_crud_security_bootstrap.model.Role;
import com.example.spring_boot_mvc_crud_security_bootstrap.repository.RoleRepository;
import com.example.spring_boot_mvc_crud_security_bootstrap.service.RoleService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Service
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepo;

    public RoleServiceImpl(RoleRepository roleRepo) {
        this.roleRepo = roleRepo;
    }

    @Override
    @Transactional(readOnly = true)
    public Set<Role> findAllDistinctRoles() {
        return roleRepo.findAllDistinctRoles();
    }


    @Override
    @Transactional(readOnly = true)
    public Role findByRoleName(String roleName) {
        return roleRepo.findByRoleName(roleName);
    }
}
