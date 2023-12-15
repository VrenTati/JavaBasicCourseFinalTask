package com.example.carshowroom.services;

import com.example.carshowroom.data.Car;
import com.example.carshowroom.data.Client;
import com.example.carshowroom.dto.CarDto;
import com.example.carshowroom.mapper.CarMapper;
import com.example.carshowroom.repositories.CarRepository;
import com.example.carshowroom.repositories.ClientRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class CarServiceTest {

    @Mock
    private CarRepository carRepository;

    @Mock
    private CarMapper carMapper;

    @Mock
    private ClientRepository clientRepository;

    @InjectMocks
    private CarService carService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getCars() {
        List<Car> cars = Arrays.asList(new Car(), new Car());
        when(carRepository.findAll()).thenReturn(cars);

        List<CarDto> carDtos = Arrays.asList(
                createCarDto(), createCarDto());
        when(carMapper.carDto(any(Car.class))).thenReturn(createCarDto());

        List<CarDto> result = carService.getCars();

        assertEquals(carDtos, result);
        verify(carRepository, times(1)).findAll();
        verify(carMapper, times(cars.size())).carDto(any(Car.class));
        verifyNoMoreInteractions(carRepository, carMapper);
    }

    @Test
    void getCar() {
        int carId = 1;
        Car car = new Car();
        when(carRepository.findById(carId)).thenReturn(Optional.of(car));

        CarDto carDto = createCarDto();
        when(carMapper.carDto(any())).thenReturn(carDto);

        Optional<CarDto> result = carService.getCar(carId);

        assertEquals(Optional.of(carDto), result);
        verify(carRepository, times(1)).findById(carId);
        verify(carMapper, times(1)).carDto(any());
        verifyNoMoreInteractions(carRepository, carMapper);
    }

    @Test
    void findCars() {
        int clientId = 1;
        Client client = new Client();
        client.setId(clientId);

        List<Car> cars = Arrays.asList(new Car(), new Car());
        when(clientRepository.findById(clientId)).thenReturn(Optional.of(client));
        when(carRepository.findByClientRequirements(
                client.getBrand(),
                client.getModel(),
                client.getYear(),
                client.getCarType(),
                client.getTransmissionType(),
                client.getFuelType(),
                client.getMaxFuelConsumption(),
                client.getMaxPrice(),
                client.getUsed())).thenReturn(cars);

        List<CarDto> carDtos = Arrays.asList(createCarDto(), createCarDto());
        when(carMapper.carDto(any())).thenReturn(createCarDto());

        List<CarDto> result = carService.findCarsForClient(clientId);

        assertEquals(carDtos, result);
        verify(clientRepository, times(1)).findById(clientId);
        verify(carRepository, times(1)).findByClientRequirements(
                client.getBrand(),
                client.getModel(),
                client.getYear(),
                client.getCarType(),
                client.getTransmissionType(),
                client.getFuelType(),
                client.getMaxFuelConsumption(),
                client.getMaxPrice(),
                client.getUsed());
        verify(carMapper, times(cars.size())).carDto(any());
        verifyNoMoreInteractions(clientRepository, carRepository, carMapper);
    }

    @Test
    void saveCar() {
        Car car = new Car();
        when(carRepository.save(car)).thenReturn(car);

        Car result = carService.saveCar(car);

        assertEquals(car, result);
        verify(carRepository, times(1)).save(car);
        verifyNoMoreInteractions(carRepository);
    }

    private CarDto createCarDto() {
        return new CarDto(
                1, "Brand", "Model", "CarType", "TransmissionType",
                "FuelType", BigDecimal.valueOf(10.0), BigDecimal.valueOf(20000.0),
                true, "photoPath");
    }

}
