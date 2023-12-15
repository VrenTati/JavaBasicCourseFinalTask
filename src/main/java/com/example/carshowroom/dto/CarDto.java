package com.example.carshowroom.dto;

import lombok.Value;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * DTO for {@link com.example.carshowroom.data.Car}
 */
@Value
public class CarDto implements Serializable {
    Integer id;
    String brand;
    String model;
    String carType;
    String transmissionType;
    String fuelType;
    BigDecimal fuelConsumption;
    BigDecimal price;
    Boolean used;
    String photoPath;

}