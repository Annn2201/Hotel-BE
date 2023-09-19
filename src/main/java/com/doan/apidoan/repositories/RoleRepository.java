package com.doan.apidoan.repositories;

import com.doan.apidoan.models.Roles;
import com.doan.apidoan.models.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Roles, Long> {
    Optional<Roles> findByRoleName(String role);
}
