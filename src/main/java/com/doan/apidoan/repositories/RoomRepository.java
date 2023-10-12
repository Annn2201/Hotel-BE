package com.doan.apidoan.repositories;

import com.doan.apidoan.models.Rooms;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoomRepository extends JpaRepository<Rooms, Long>, PagingAndSortingRepository<Rooms, Long>, JpaSpecificationExecutor<Rooms> {
    Page<Rooms> findAll(Specification<Rooms> roomsSpecification, Pageable pageable);
    Optional<Rooms> findByRoomCode(String roomCode);

}
