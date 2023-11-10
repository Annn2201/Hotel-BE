package com.doan.apidoan.services.impl;

import com.doan.apidoan.repositories.RoomUserRepository;
import com.doan.apidoan.services.RoomUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoomUserServiceImpl implements RoomUserService {
    private final RoomUserRepository roomUserRepository;
    @Override
    public void deleteBookingRoom(Long id) {
        roomUserRepository.deleteById(id);
    }
}
