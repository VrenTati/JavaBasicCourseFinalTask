package com.example.carshowroom.data;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "cars")
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "brand", nullable = false)
    private String brand;

    @Column(name = "model", nullable = false)
    private String model;

    @Column(name = "car_type", length = 50)
    private String carType;

    @Column(name = "transmission_type", length = 50)
    private String transmissionType;

    @Column(name = "fuel_type", length = 50)
    private String fuelType;

    @Column(name = "fuel_consumption", precision = 5, scale = 2)
    private BigDecimal fuelConsumption;

    @Column(name = "price", precision = 10, scale = 2)
    private BigDecimal price;

    @Column(name = "used")
    private Boolean used;

    @Column(name = "photo_path")
    private String photoPath;

    @Column(name = "year")
    private Integer year;
}