package com.l2i_e_commerce.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;

@Data
@Entity
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String role;

    String firstname;

    String lastname;

    @Column(unique = true)
    String username;

    @Column(unique = true)
    String email;

    String password;

    @JsonIgnoreProperties("users")
    @ManyToMany(cascade = { CascadeType.MERGE, CascadeType.REFRESH })
    List<Address> addresses;

    public User(@NonNull String role, @NonNull String firstname, @NonNull String lastname,
                @NonNull String email, @NonNull String password, @NonNull List<Address> addresses) {
        this.role = role;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.password = password;
        this.addresses = addresses;
    }

    public User() {

    }
}