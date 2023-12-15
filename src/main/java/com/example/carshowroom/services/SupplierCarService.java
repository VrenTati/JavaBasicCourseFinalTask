package com.example.carshowroom.services;

import com.example.carshowroom.data.Car;
import com.example.carshowroom.data.Supplier;
import com.example.carshowroom.data.SupplierCar;
import com.example.carshowroom.repositories.CarRepository;
import com.example.carshowroom.repositories.SupplierCarRepository;
import com.example.carshowroom.repositories.SupplierRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class SupplierCarService {
    private final SupplierCarRepository supplierCarRepository;
    private final SupplierService supplierService;
    private final CarService carService;
    private final CarRepository carRepository;
    private final SupplierRepository supplierRepository;

    public List<Supplier> getCarSuppliers(int id){
        return supplierCarRepository.findSuppliersByCarId(id);
    }
    public List<Integer> getCarsBySupplier(Supplier supplier){
        List<Integer> cars = new ArrayList<>();
        List<SupplierCar> supplierCars = supplierCarRepository.getSupplierCarsBySupplier(supplier);
        for (SupplierCar sc : supplierCars){
            cars.add(sc.getCar().getId());
        }
        return cars;
    }

    public void addCarSuppliersBySuppliersIds(List<Integer> suppIds, Car car){
        List<Supplier> suppliers = supplierService.getSuppliersByIds(suppIds);
        List<SupplierCar> carSupp = new ArrayList<>();
        for(Supplier s : suppliers){
            SupplierCar supplierCar = new SupplierCar();
            supplierCar.setSupplier(s);
            supplierCar.setCar(car);
            carSupp.add(supplierCar);
        }
        supplierCarRepository.saveAll(carSupp);
    }

    public void addCarSuppliersByCarsIds(List<Integer> carsIds, Supplier supplier){
        List<Car> cars = carService.getCarsByIds(carsIds);
        List<SupplierCar> carSupp = new ArrayList<>();
        for (Car c : cars){
            SupplierCar supplierCar = new SupplierCar();
            supplierCar.setSupplier(supplier);
            supplierCar.setCar(c);
            carSupp.add(supplierCar);
        }
        supplierCarRepository.saveAll(carSupp);
    }


    public void updateSuppliersForCar(Integer carId, List<Integer> newSupplierIds) {
        List<SupplierCar> existingSuppliers = supplierCarRepository.findByCarId(carId);

        supplierCarRepository.deleteAll(existingSuppliers);

        for (Integer newSupplierId : newSupplierIds) {

                SupplierCar newSupplierCar = new SupplierCar();
                Optional<Car> car = carRepository.findById(carId);
                newSupplierCar.setCar(car.get());

                Optional<Supplier> optionalSupplier = supplierRepository.findById(newSupplierId);
                if (optionalSupplier.isPresent()) {
                    Supplier supplier = optionalSupplier.get();
                    newSupplierCar.setSupplier(supplier);
                    supplierCarRepository.save(newSupplierCar);
                }
        }
    }
}
