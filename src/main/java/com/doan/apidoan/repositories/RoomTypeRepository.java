package com.doan.apidoan.repositories;

import com.doan.apidoan.models.RoomTypes;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoomTypeRepository extends JpaRepository<RoomTypes, Long> {
    Optional<RoomTypes> findByTypeName(String name);
}
