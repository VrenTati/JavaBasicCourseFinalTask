package com.example.carshowroom.services;

import com.example.carshowroom.data.Supplier;
import com.example.carshowroom.repositories.SupplierCarRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class SupplierCarServiceTest {
    @Mock
    private SupplierCarRepository supplierCarRepository;

    @InjectMocks
    private SupplierCarService supplierCarService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void getCarSuppliers() {
        int carId = 1;

        List<Supplier> expectedSuppliers = Arrays.asList(new Supplier(), new Supplier());
        when(supplierCarRepository.findSuppliersByCarId(carId)).thenReturn(expectedSuppliers);

        List<Supplier> result = supplierCarService.getCarSuppliers(carId);

        assertEquals(expectedSuppliers, result);
        verify(supplierCarRepository, times(1)).findSuppliersByCarId(carId);
        verifyNoMoreInteractions(supplierCarRepository);
    }

}