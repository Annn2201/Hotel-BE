package com.doan.apidoan.repositories;

import com.doan.apidoan.models.RoomRanks;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoomRankRepository extends JpaRepository<RoomRanks, Long> {
    Optional<RoomRanks> findByRankName(String name);
}
