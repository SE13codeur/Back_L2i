package com.l2i_e_commerce.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;

@Data
@Entity
@AllArgsConstructor
public class Address {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id;

	@NonNull
	String street;

	@NonNull
	String zip;

	@NonNull
	String city;

	@NonNull
	String country;

	@NonNull
	@JsonIgnoreProperties("address")
	@ManyToOne
	User user;

	public Address() {

	}
}