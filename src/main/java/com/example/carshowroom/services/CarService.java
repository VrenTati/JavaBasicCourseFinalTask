package com.example.carshowroom.services;

import com.example.carshowroom.data.Car;
import com.example.carshowroom.data.Client;
import com.example.carshowroom.dto.CarDto;
import com.example.carshowroom.mapper.CarMapper;
import com.example.carshowroom.repositories.CarRepository;
import com.example.carshowroom.repositories.ClientRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CarService {

    private final CarRepository carRepository;
    private final CarMapper carMapper;
    private final ClientRepository clientRepository;

    public List<CarDto> getCars(){
        return carRepository.findAll().stream().map(carMapper::carDto).toList();
    }

    public Optional<CarDto> getCar(int id){
        return carRepository.findById(id).map(carMapper::carDto);
    }

    public List<CarDto> findCarsForClient(int id){
        Optional<Client> client = clientRepository.findById(id);
        return carRepository.findByClientRequirements(
                client.get().getBrand(),
                client.get().getModel(),
                client.get().getYear(),
                client.get().getCarType(),
                client.get().getTransmissionType(),
                client.get().getFuelType(),
                client.get().getMaxFuelConsumption(),
                client.get().getMaxPrice(),
                client.get().getUsed()
        ).stream().map(carMapper::carDto).toList();
    }

    public Car saveCar(Car car) {
        return carRepository.save(car);
    }

    public List<Car> getCarsByIds(List<Integer> carIds){
        return carRepository.findAllById(carIds);
    }

}


