package com.doan.apidoan.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Objects;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Rooms {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "room_id")
    private long roomId;
    @Basic
    @Column(name = "room_code")
    private String roomCode;
    @Basic
    @Column(name = "price_per_night")
    private Long pricePerNight;
    @ManyToOne
    @JoinColumn(name = "user")
    private Users user;
    @ManyToOne
    @JoinColumn(name = "type_id")
    private RoomTypes roomType;
    @ManyToOne
    @JoinColumn(name = "rank_id")
    private RoomRanks roomRank;
    private String description;
    private LocalDateTime checkInTime;
    private LocalDateTime checkOutTime;
    private int population;
    private String roomName;
}
