package com.doan.apidoan.services;

import com.doan.apidoan.dtos.RoomDTO;
import com.doan.apidoan.models.Users;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.time.LocalDate;
import java.util.List;

public interface RoomService {
    List<RoomDTO> findAll(String rank, String type, String sortBy);
    RoomDTO getRoomByRoomCode(String roomCode);
    void bookRoomByUser(Users users, String roomCode, String startDate, String endDate);

}
