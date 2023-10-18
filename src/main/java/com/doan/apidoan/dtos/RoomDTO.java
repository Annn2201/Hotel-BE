package com.doan.apidoan.dtos;

import com.doan.apidoan.models.Image;
import com.doan.apidoan.models.RoomRanks;
import com.doan.apidoan.models.RoomTypes;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RoomDTO {
    private String roomCode;
    private String roomType;
    private String roomRank;
    private Double pricePerNight;
    private String description;
    private String roomName;
    private List<String> images;
    private Integer population;
}
