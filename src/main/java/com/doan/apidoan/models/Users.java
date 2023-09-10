package com.doan.apidoan.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Objects;

@Entity
@Getter
@Setter
public class Users {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "user_id")
    private long userId;
    @Basic
    @Column(name = "username")
    private String username;
    @Basic
    @Column(name = "first_name")
    private String firstName;
    @Basic
    @Column(name = "last_name")
    private String lastName;
    @Basic
    @Column(name = "phone")
    private String phone;
    @Basic
    @Column(name = "email")
    private String email;
    @Basic
    @Column(name = "password")
    private String password;
    @Basic
    @Column(name = "identify_number")
    private String identifyNumber;
    @Basic
    @Column(name = "isAdmin")
    private Byte isAdmin;

    @OneToMany(mappedBy = "user")
    List<Rooms> rooms;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Users users = (Users) o;
        return userId == users.userId && Objects.equals(username, users.username) && Objects.equals(firstName, users.firstName) && Objects.equals(lastName, users.lastName) && Objects.equals(phone, users.phone) && Objects.equals(email, users.email) && Objects.equals(password, users.password) && Objects.equals(identifyNumber, users.identifyNumber) && Objects.equals(isAdmin, users.isAdmin);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, username, firstName, lastName, phone, email, password, identifyNumber, isAdmin);
    }
}
