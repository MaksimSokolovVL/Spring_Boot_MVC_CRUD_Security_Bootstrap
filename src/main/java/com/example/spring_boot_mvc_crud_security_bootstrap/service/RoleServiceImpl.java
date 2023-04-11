package com.example.spring_boot_mvc_crud_security_bootstrap.service;


import com.example.spring_boot_mvc_crud_security_bootstrap.model.Role;
import com.example.spring_boot_mvc_crud_security_bootstrap.repository.RoleRepository;
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
    @Transactional
    public Set<Role> findAllDistinctRoles() {
        return roleRepo.findAllDistinctRoles();
    }


    @Override
    @Transactional
    public Role findByRoleName(String roleName) {
        return roleRepo.findByRoleName(roleName);
    }
}
