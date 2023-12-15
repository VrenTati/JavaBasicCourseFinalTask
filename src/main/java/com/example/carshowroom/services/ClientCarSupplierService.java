package com.example.carshowroom.services;

import com.example.carshowroom.data.ClientCarSupplier;
import com.example.carshowroom.data.Supplier;
import com.example.carshowroom.repositories.CarRepository;
import com.example.carshowroom.repositories.ClientCarSupplierRepository;
import com.example.carshowroom.repositories.ClientRepository;
import com.example.carshowroom.repositories.SupplierRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ClientCarSupplierService {

    private final ClientCarSupplierRepository clientCarSupplierRepository;
    private final SupplierRepository supplierRepository;
    private final ClientRepository clientRepository;
    private final CarRepository carRepository;

    public ClientCarSupplier addOrder(int client_id, int car_id, int supplier_id, String comment){
        ClientCarSupplier clientCarSupplier = new ClientCarSupplier();
        clientCarSupplier.setClient(clientRepository.findById(client_id).get());
        clientCarSupplier.setCar(carRepository.findById(car_id).get());
        Optional<Supplier> supp = supplierRepository.findById(supplier_id);
        supp.ifPresent(clientCarSupplier::setSupplier);
        clientCarSupplier.setComments(comment);
        LocalDateTime localDateTime = LocalDateTime.now();
        Instant instant = localDateTime.atZone(ZoneOffset.UTC).toInstant();
        clientCarSupplier.setCreatedAt(instant);
        return clientCarSupplierRepository.save(clientCarSupplier);
    }

    public List<ClientCarSupplier> getAllOrders(){
        return clientCarSupplierRepository.findAll();
    }

    public Optional<ClientCarSupplier> getOrderById(int id){
        return clientCarSupplierRepository.findById(id);
    }


}
