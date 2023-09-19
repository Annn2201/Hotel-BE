package com.doan.apidoan.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
    @Column(name = "room_type")
    private String roomType;
    @Basic
    @Column(name = "prime_per_night")
    private Long primePerNight;
    @Basic
    @Column(name = "available_room")
    private String availableRoom;
    @ManyToOne
    @JoinColumn(name = "user")
    private Users user;

}
