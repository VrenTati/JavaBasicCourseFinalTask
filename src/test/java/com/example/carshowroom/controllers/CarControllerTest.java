package com.example.carshowroom.controllers;

import com.example.carshowroom.data.Client;
import com.example.carshowroom.dto.CarDto;
import com.example.carshowroom.services.CarService;
import com.example.carshowroom.services.ClientService;
import com.example.carshowroom.services.SupplierCarService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ui.Model;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CarControllerTest {

    @Mock
    private CarService carService;

    @Mock
    private ClientService clientService;

    @Mock
    private SupplierCarService supplierCarService;

    @InjectMocks
    private CarController carController;

    @Test
    void getCars() {
        Model model = mock(Model.class);

        String viewName = carController.getCars(model);

        assertEquals("cars", viewName);
        verify(model).addAttribute("cars", carService.getCars());
    }

    @Test
    void getCar() {
        Model model = mock(Model.class);
        int carId = 1;

        CarDto carDto = new CarDto(
                1, "Toyota", "Camry", "Sedan", "Automatic", "Petrol",
                BigDecimal.valueOf(8.5), BigDecimal.valueOf(25000), false, "/path/to/photo"
        );

        when(carService.getCar(anyInt())).thenReturn(Optional.of(carDto));

        String viewName = carController.getCar(carId, model);

        assertEquals("car_info", viewName);
        verify(model).addAttribute("car", carDto);
        verify(model).addAttribute("cars", carService.getCars());
        verify(model).addAttribute("suppliers", supplierCarService.getCarSuppliers(carId));
        verify(model).addAttribute("clients", clientService.getClientByCar(carId));
    }

    @Test
    void getCarNoCarFound() {
        Model model = mock(Model.class);
        int carId = 1;

        when(carService.getCar(carId)).thenReturn(Optional.empty());

        String viewName = carController.getCar(carId, model);

        assertEquals("no_car", viewName);
    }

    @Test
    void carFilterNoCarsFound() {
        Model model = mock(Model.class);
        int clientId = 1;

        when(clientService.getClient(clientId)).thenReturn(Optional.of(new Client()));
        when(carService.findCarsForClient(clientId)).thenReturn(Collections.emptyList());

        String viewName = carController.carFilter(model, clientId);

        assertEquals("no_car", viewName);
    }
}
