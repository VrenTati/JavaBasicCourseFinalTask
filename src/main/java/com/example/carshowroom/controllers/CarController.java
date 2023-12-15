package com.example.carshowroom.controllers;

import com.example.carshowroom.data.Car;
import com.example.carshowroom.data.Client;
import com.example.carshowroom.dto.CarDto;
import com.example.carshowroom.services.CarService;
import com.example.carshowroom.services.ClientService;
import com.example.carshowroom.services.SupplierCarService;
import com.example.carshowroom.services.SupplierService;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;
import java.util.random.RandomGenerator;

@Controller
@AllArgsConstructor
public class CarController {

    private final CarService carService;
    private final ClientService clientService;
    private final SupplierCarService supplierCarService;
    private final SupplierService supplierService;

    @GetMapping("/cars")
    public String getCars(Model model){
        model.addAttribute("cars", carService.getCars());
        model.addAttribute("suppliers", supplierService.getAllSuppliers());
        return "car/cars";
    }

    @GetMapping("/car_info/{id}")
    public String getCar(@PathVariable int id, Model model){
        Optional<CarDto> car = carService.getCar(id);
        if(car.isPresent()){
            model.addAttribute("car", car.get());
            model.addAttribute("cars", carService.getCars());
            model.addAttribute("suppliers", supplierCarService.getCarSuppliers(id));
            model.addAttribute("clients", clientService.getClientByCar(id));
            return "car/car_info";
        } else {
            return "car/no_car";
        }
    }

    @GetMapping("/car_filter/{id}")
    public String carFilter(Model model, @PathVariable int id){
        Optional<Client> client = clientService.getClient(id);
        client.ifPresent(value->model.addAttribute("client", value));
        //model.addAttribute("client", client.get());
        List<CarDto> cars = carService.findCarsForClient(id);
        model.addAttribute("supp", supplierCarService);
        model.addAttribute("cars", cars);
        if (!cars.isEmpty()){
            return "car/car_filter";
        }
        return "car/no_car";
    }

    @GetMapping("/add_car")
    public String showUploadForm(Model model) {
        model.addAttribute("suppliers", supplierService.getAllSuppliers());
        return "car/add_car";
    }

    @PostMapping(path = "/add_car", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    public String saveCar(
            @RequestParam("model") String carModel,
            @RequestParam("brand") String brand,
            @RequestParam("carType") String carType,
            @RequestParam("year") Integer year,
            @RequestParam("fuelType") String fuelType,
            @RequestParam("transmissionType") String transmissionType,
            @RequestParam("fuelConsumption") BigDecimal fuelConsumption,
            @RequestParam("price") BigDecimal Price,
            @RequestParam(value = "used", defaultValue = "false") boolean used,
            @RequestParam(name = "suppliers", defaultValue = "") List<Integer> supplierIds,
            @RequestParam("image") MultipartFile imageFile) {
        Car car = new Car();
        if (!imageFile.isEmpty()) {
            try {
                String currentDirectory = System.getProperty("user.dir");
                String uploadDir = currentDirectory + "/src/main/resources/static/images/cars/";
                //String uploadDir = "D:\\JavaBasicCourse\\CarShowRoom\\src\\main\\resources\\static\\images\\cars";
                File uploadPath = new File(uploadDir);
                if (!uploadPath.exists()) {
                    uploadPath.mkdirs();
                }
                String imageName = UUID.randomUUID() + "_" + imageFile.getOriginalFilename();
                String imagePath = uploadDir + imageName;
                imageFile.transferTo(new File(imagePath));
                car.setPhotoPath(imageName);
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        } else {
            car.setPhotoPath(null);
        }
        car.setBrand(brand);
        car.setModel(carModel);
        car.setCarType(carType);
        car.setPrice(Price);
        car.setYear(year);
        car.setFuelConsumption(fuelConsumption);
        car.setFuelType(fuelType);
        car.setTransmissionType(transmissionType);
        car.setUsed(used);
        Car car1 = carService.saveCar(car);
        if(!supplierIds.isEmpty())
            supplierCarService.addCarSuppliersBySuppliersIds(supplierIds, car1);
        return "redirect:/cars";
    }

}
