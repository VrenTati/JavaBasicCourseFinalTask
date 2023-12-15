package com.example.carshowroom.services;

import com.example.carshowroom.data.Car;
import com.example.carshowroom.data.Client;
import com.example.carshowroom.repositories.CarRepository;
import com.example.carshowroom.repositories.ClientRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ClientService {

    private final ClientRepository clientRepository;
    private final CarRepository carRepository;

    public List<Client> getClients(){
        return clientRepository.findAll();
    }

    public Optional<Client> getClient(int id){
        return clientRepository.findById(id);
    }

    public List<Client> getClientByCar(int id){
        Optional<Car> car = carRepository.findById(id);
        return car.map(value -> clientRepository.findClientByCar(
                value.getBrand(),
                value.getModel(),
                value.getCarType(),
                value.getTransmissionType(),
                value.getFuelType(),
                value.getUsed(),
                value.getFuelConsumption(),
                value.getYear(),
                value.getPrice()
        )).orElse(null);
    }

    public void updateClient(Client client){
        clientRepository.save(client);
    }

    public Client addClient(Client newClient) {
        return clientRepository.save(newClient);
    }

    public List<Client> findClientsWithoutSuppliers(){
        return clientRepository.findClientsWithoutSuppliers();
    }

    public void deleteById(int id){
        clientRepository.deleteById(id);
    }

    public void setClientValues(String carModel,
                                 String brand,
                                 String carType,
                                 String fuelType,
                                 String transmissionType,
                                 Integer year,
                                 BigDecimal fuelConsumption,
                                 BigDecimal maxPrice,
                                 boolean used,
                                 Client newClient) {
        if (carModel.isEmpty())
            newClient.setModel(null);
        else
            newClient.setModel(carModel);
        if (brand.isEmpty())
            newClient.setBrand(null);
        else
            newClient.setBrand(brand);
        if (carType.isEmpty())
            newClient.setCarType(null);
        else
            newClient.setCarType(carType);
        if (fuelType.isEmpty())
            newClient.setFuelType(null);
        else
            newClient.setFuelType(fuelType);
        if (transmissionType.isEmpty())
            newClient.setTransmissionType(null);
        else
            newClient.setTransmissionType(transmissionType);
        newClient.setYear(year);
        newClient.setMaxFuelConsumption(fuelConsumption);
        newClient.setMaxPrice(maxPrice);
        newClient.setUsed(used);
    }
 }
