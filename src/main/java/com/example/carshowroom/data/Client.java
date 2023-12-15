package com.example.carshowroom.data;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Entity
@Table(name = "clients")
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    /*@Column(name = "client_name", nullable = false)
    private String clientName;*/

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "car_type", length = 50)
    private String carType;

    @Column(name = "transmission_type", length = 50)
    private String transmissionType;

    @Column(name = "fuel_type", length = 50)
    private String fuelType;

    @Column(name = "max_fuel_consumption", precision = 5, scale = 2)
    private BigDecimal maxFuelConsumption;

    @Column(name = "max_price", precision = 10, scale = 2)
    private BigDecimal maxPrice;

    @Column(name = "used")
    private Boolean used;

    @Column(name = "year")
    private Integer year;

    @Column(name = "brand", length = 250)
    private String brand;

    @Column(name = "model", length = 250)
    private String model;

    @Column(name = "name")
    private String name;

    @Column(name = "lastname")
    private String lastname;

}