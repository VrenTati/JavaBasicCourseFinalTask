package com.example.carshowroom.repositories;

import com.example.carshowroom.data.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;

public interface ClientRepository extends JpaRepository<Client, Integer> {
    @Query("SELECT c FROM Client c " +
            "WHERE " +
            "(c.brand IS NULL OR c.brand = :brand) AND " +
            "(c.model IS NULL OR c.model = :model) AND " +
            "(c.carType IS NULL OR c.carType = :carType) AND " +
            "(c.transmissionType IS NULL OR c.transmissionType = :transmissionType) AND " +
            "(c.fuelType IS NULL OR c.fuelType = :fuelType) AND " +
            "(c.used IS NULL OR c.used = :used) AND " +
            "(c.maxFuelConsumption IS NULL OR c.maxFuelConsumption <= :maxFuelConsumption) AND " +
            "(c.year IS NULL OR c.year = :year) AND " +
            "(c.maxPrice IS NULL OR c.maxPrice <= :maxPrice) AND " +
            "c.id NOT IN (SELECT DISTINCT cc.client.id FROM ClientCarSupplier cc)")
    List<Client> findClientByCar(@Param("brand") String brand,
                                  @Param("model") String model,
                                  @Param("carType") String carType,
                                  @Param("transmissionType") String transmissionType,
                                  @Param("fuelType") String fuelType,
                                  @Param("used") Boolean used,
                                  @Param("maxFuelConsumption") BigDecimal maxFuelConsumption,
                                  @Param("year") Integer year,
                                  @Param("maxPrice") BigDecimal maxPrice);

    @Query("SELECT c FROM Client c WHERE c.id " +
            "NOT IN (SELECT DISTINCT ccs.client.id " +
            "FROM ClientCarSupplier ccs)")
    List<Client> findClientsWithoutSuppliers();
}