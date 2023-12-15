package com.example.carshowroom.services;

import com.example.carshowroom.data.Car;
import com.example.carshowroom.data.Client;
import com.example.carshowroom.repositories.CarRepository;
import com.example.carshowroom.repositories.ClientRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class ClientServiceTest {

    @Mock
    private ClientRepository clientRepository;

    @Mock
    private CarRepository carRepository;

    @InjectMocks
    private ClientService clientService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getClients() {
        List<Client> clients = Arrays.asList(new Client(), new Client());
        when(clientRepository.findAll()).thenReturn(clients);

        List<Client> result = clientService.getClients();

        assertEquals(clients, result);
        verify(clientRepository, times(1)).findAll();
        verifyNoMoreInteractions(clientRepository);
    }

    @Test
    void getClient() {
        int clientId = 1;
        Client client = new Client();
        when(clientRepository.findById(clientId)).thenReturn(Optional.of(client));

        Optional<Client> result = clientService.getClient(clientId);

        assertEquals(Optional.of(client), result);
        verify(clientRepository, times(1)).findById(clientId);
        verifyNoMoreInteractions(clientRepository);
    }

    @Test
    void getClientByCar() {
        int carId = 1;
        Car car = new Car();
        car.setId(carId);

        List<Client> clients = Arrays.asList(new Client(), new Client());
        when(carRepository.findById(carId)).thenReturn(Optional.of(car));
        when(clientRepository.findClientByCar(
                car.getBrand(),
                car.getModel(),
                car.getCarType(),
                car.getTransmissionType(),
                car.getFuelType(),
                car.getUsed(),
                car.getFuelConsumption(),
                car.getYear(),
                car.getPrice())).thenReturn(clients);

        List<Client> result = clientService.getClientByCar(carId);

        assertEquals(clients, result);
        verify(carRepository, times(1)).findById(carId);
        verify(clientRepository, times(1)).findClientByCar(
                car.getBrand(),
                car.getModel(),
                car.getCarType(),
                car.getTransmissionType(),
                car.getFuelType(),
                car.getUsed(),
                car.getFuelConsumption(),
                car.getYear(),
                car.getPrice());
        verifyNoMoreInteractions(carRepository, clientRepository);
    }
}
