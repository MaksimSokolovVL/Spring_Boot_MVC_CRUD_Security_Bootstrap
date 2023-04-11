package com.example.spring_boot_mvc_crud_security_bootstrap.repository;


import com.example.spring_boot_mvc_crud_security_bootstrap.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Set;

public interface RoleRepository extends JpaRepository<Role, Long> {
    @Query("SELECT DISTINCT r FROM Role r")
    Set<Role> findAllDistinctRoles();

    Role findByRoleName(String roleName);
}
