package com.example.carshowroom.mapper;

import com.example.carshowroom.data.Car;
import com.example.carshowroom.dto.CarDto;
import com.example.carshowroom.repositories.CarRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.ApplicationScope;

@ApplicationScope
@Service
public class CarMapper {
    public CarDto carDto(Car car){
        if(car.getPhotoPath() != null){
            return new CarDto(car.getId(), car.getBrand(), car.getModel(), car.getCarType(),
                    car.getTransmissionType(), car.getFuelType(), car.getFuelConsumption(),
                    car.getPrice(), car.getUsed(), car.getPhotoPath());
        } else {
            return new CarDto(car.getId(), car.getBrand(), car.getModel(), car.getCarType(),
                    car.getTransmissionType(), car.getFuelType(), car.getFuelConsumption(),
                    car.getPrice(), car.getUsed(), "alt.jpg");
        }
    }
}
