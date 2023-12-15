package com.example.carshowroom.repositories;

import com.example.carshowroom.data.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.util.List;

public interface CarRepository extends JpaRepository<Car, Integer> {
    @Query("SELECT c FROM Car c WHERE " +
            "(:brand IS NULL OR c.brand = :brand) AND " +
            "(:model IS NULL OR c.model = :model) AND " +
            "(:year IS NULL OR c.year = :year) AND " +
            "(:carType IS NULL OR c.carType = :carType) AND " +
            "(:transmissionType IS NULL OR c.transmissionType = :transmissionType) AND " +
            "(:fuelType IS NULL OR c.fuelType = :fuelType) AND " +
            "(:fuelConsumption IS NULL OR c.fuelConsumption <= :fuelConsumption) AND " +
            "(:price IS NULL OR c.price <= :price) AND " +
            "(:used IS NULL OR c.used = :used)")
    List<Car> findByClientRequirements(
            String brand,
            String model,
            Integer year,
            String carType,
            String transmissionType,
            String fuelType,
            BigDecimal fuelConsumption,
            BigDecimal price,
            Boolean used);

}