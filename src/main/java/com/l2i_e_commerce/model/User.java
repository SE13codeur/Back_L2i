package com.l2i_e_commerce.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.List;

@AllArgsConstructor
@Data
@Entity
@RequiredArgsConstructor
@NoArgsConstructor(force = true)
@Builder
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(unique = true, nullable = false)
    @NonNull
    String username;

    @NonNull
    String password;

    @NonNull
    @ManyToMany(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    List<Role> roles;
}