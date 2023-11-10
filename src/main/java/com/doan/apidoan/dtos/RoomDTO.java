package com.doan.apidoan.dtos;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RoomDTO {
    private Long roomUserId;
    private String roomCode;
    private String roomType;
    private String roomRank;
    private Double pricePerNight;
    private String description;
    private String roomName;
    private List<String> images;
    private Integer population;
    private String startDate;
    private String endDate;
    private Boolean isCheckIn;
    private Boolean isCheckOut;
}
