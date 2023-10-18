package com.doan.apidoan.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Objects;

@Table(name = "room_user", schema = "khachsan_doan", catalog = "")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class RoomUser {
    @Basic
    @Column(name = "start_date")
    private LocalDate startDate;
    @Basic
    @Column(name = "end_date")
    private LocalDate endDate;
    @Basic
    @Column(name = "rooms_id")
    private Long roomsId;
    @Basic
    @Column(name = "users_id")
    private Long usersId;
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private Long id;

}
