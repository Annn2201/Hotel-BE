package com.doan.apidoan.repositories;

import com.doan.apidoan.dtos.LoginDTO;
import com.doan.apidoan.models.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserReporitory extends JpaRepository<Users, Long> {
    Optional<Users> findByUsername(String username);
}
