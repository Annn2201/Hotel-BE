package com.doan.apidoan.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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
    private Long roomId;
    @Basic
    @Column(name = "room_code")
    private String roomCode;
    @Basic
    @Column(name = "price_per_night")
    private Double pricePerNight;
    @ManyToOne
    @JoinColumn(name = "type_id")
    private RoomTypes roomType;
    @ManyToOne
    @JoinColumn(name = "rank_id")
    private RoomRanks roomRank;
    private String description;
    private Integer population;
    private String roomName;
    private LocalDate startDate;
    private LocalDate endDate;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "room_image",
            joinColumns = @JoinColumn(name = "room_id"),
            inverseJoinColumns = @JoinColumn(name = "image_id"))
    private List<Image> roomImage = new ArrayList<>();
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "room_user",
            joinColumns = @JoinColumn(name = "rooms_id"),
            inverseJoinColumns = @JoinColumn(name = "users_id"))
    private List<Users> roomUser = new ArrayList<>();
}
