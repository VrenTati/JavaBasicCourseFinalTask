package com.example.carshowroom.services;

import com.example.carshowroom.data.Supplier;
import com.example.carshowroom.repositories.SupplierRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class SupplierServiceTest {

    @Mock
    private SupplierRepository supplierRepository;

    @InjectMocks
    private SupplierService supplierService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllSuppliers() {
        List<Supplier> expectedSuppliers = Arrays.asList(
                new Supplier(),
                new Supplier()
        );
        when(supplierRepository.findAll()).thenReturn(expectedSuppliers);

        List<Supplier> actualSuppliers = supplierService.getAllSuppliers();

        assertThat(actualSuppliers).isEqualTo(expectedSuppliers);
        verify(supplierRepository, times(1)).findAll();
    }

    @Test
    void getSuppliersByIds() {
        List<Integer> supplierIds = Arrays.asList(1, 2);
        List<Supplier> expectedSuppliers = Arrays.asList(
                new Supplier(),
                new Supplier()
        );
        when(supplierRepository.findAllById(supplierIds)).thenReturn(expectedSuppliers);

        List<Supplier> actualSuppliers = supplierService.getSuppliersByIds(supplierIds);

        assertThat(actualSuppliers).isEqualTo(expectedSuppliers);
        verify(supplierRepository, times(1)).findAllById(supplierIds);
    }


}