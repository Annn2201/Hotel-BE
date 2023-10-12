package com.doan.apidoan.dtos;

import com.doan.apidoan.models.RoomRanks;
import com.doan.apidoan.models.RoomTypes;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RoomDTO {
    private String roomCode;
    private String roomType;
    private String roomRank;
    private Long pricePerNight;
    private String description;
    private String roomName;
}
