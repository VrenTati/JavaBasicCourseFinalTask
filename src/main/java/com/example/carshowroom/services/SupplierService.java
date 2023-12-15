package com.example.carshowroom.services;

import com.example.carshowroom.data.Supplier;
import com.example.carshowroom.data.SupplierCar;
import com.example.carshowroom.repositories.CarRepository;
import com.example.carshowroom.repositories.SupplierCarRepository;
import com.example.carshowroom.repositories.SupplierRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class SupplierService {
    private final SupplierRepository supplierRepository;
    private final CarRepository carRepository;
    private final SupplierCarRepository supplierCarRepository;

    public List<Supplier> getAllSuppliers() {
        return supplierRepository.findAll();
    }

    public List<Supplier> getSuppliersByIds(List<Integer> suppIds) {
        return supplierRepository.findAllById(suppIds);
    }

    public Supplier saveSupplier(Supplier supplier) {
        return supplierRepository.save(supplier);
    }

    public Optional<Supplier> getSupplierById(Integer id) {
        return supplierRepository.findById(id);
    }

    public void updateSupplier(Supplier supplier, List<Integer> carsIds) {
        List<SupplierCar> existingPairs = supplierCarRepository.getSupplierCarsBySupplier(supplier);
        supplierCarRepository.deleteAll(existingPairs);
        if(!carsIds.isEmpty()) {
            for (Integer id : carsIds) {
                SupplierCar supplierCar = new SupplierCar();
                supplierCar.setCar(carRepository.findById(id).get());
                supplierCar.setSupplier(supplier);
                supplierCarRepository.save(supplierCar);
            }
        }
    }
}
