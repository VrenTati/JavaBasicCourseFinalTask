package com.example.carshowroom.repositories;

import com.example.carshowroom.data.Car;
import com.example.carshowroom.data.Supplier;
import com.example.carshowroom.data.SupplierCar;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SupplierCarRepository extends JpaRepository<SupplierCar, Integer> {
    @Query("SELECT sc.supplier FROM SupplierCar sc WHERE sc.car.id = :carId")
    List<Supplier> findSuppliersByCarId(@Param("carId") Integer carId);

    List<SupplierCar> findByCarId(Integer carId);

    List<SupplierCar> getSupplierCarsBySupplier(Supplier supplier);
}