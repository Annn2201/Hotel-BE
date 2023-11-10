package com.doan.apidoan.repositories;

import com.doan.apidoan.models.RoomUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RoomUserRepository extends JpaRepository<RoomUser, Long> {
    List<RoomUser> findByRoomsId(Long aLong);
    List<RoomUser> findByUsersId(Long id);
    void deleteByUsersId(Long id);
}
