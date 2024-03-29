package com.l2i_e_commerce.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "role_id")
    private Role role = new Role();

    private String firstname;

    private String lastname;

    @Column(unique = true)
    private String username;

    @Column(unique = true)
    private String email;

    private String password;

    @JsonIgnoreProperties("users")
    @ManyToMany(cascade = { CascadeType.MERGE, CascadeType.REFRESH })
    private List<Address> addresses;

    public User(@NonNull Role role, @NonNull String firstname, @NonNull String lastname,
                @NonNull String email, @NonNull String password, @NonNull List<Address> addresses) {
        this.role = role;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.password = password;
        this.addresses = addresses;
    }

}
