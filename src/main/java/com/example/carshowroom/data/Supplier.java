package com.example.carshowroom.data;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "suppliers")
public class Supplier {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "email", nullable = false)
    private String email;

    @OneToMany(mappedBy = "supplier")
    private Set<SupplierCar> supplierCars = new LinkedHashSet<>();

    @Column(name = "price", nullable = false)
    private Double price;

    @Column(name = "deliver_days", nullable = false)
    private Integer deliverDays;

}