package com.doan.apidoan.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Entity
@Getter
@Setter
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Rooms rooms = (Rooms) o;
        return roomId == rooms.roomId && Objects.equals(roomCode, rooms.roomCode) && Objects.equals(roomType, rooms.roomType) && Objects.equals(primePerNight, rooms.primePerNight) && Objects.equals(availableRoom, rooms.availableRoom) && Objects.equals(user, rooms.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(roomId, roomCode, roomType, primePerNight, availableRoom, user);
    }
}
