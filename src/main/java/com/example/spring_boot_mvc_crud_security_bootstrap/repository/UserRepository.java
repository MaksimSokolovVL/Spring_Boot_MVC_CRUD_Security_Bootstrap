package com.example.spring_boot_mvc_crud_security_bootstrap.repository;


import com.example.spring_boot_mvc_crud_security_bootstrap.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}
