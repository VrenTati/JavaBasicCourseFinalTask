package com.example.carshowroom.repositories;

import com.example.carshowroom.data.Client;
import com.example.carshowroom.data.ClientCarSupplier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ClientCarSupplierRepository extends JpaRepository<ClientCarSupplier, Integer> {
    /*@Query("SELECT c FROM Client c WHERE NOT EXISTS " +
            "(SELECT 1 FROM ClientCarSupplier cc WHERE cc.client.id = c.id)")
    List<Client> findClientsNotInClientCarSupplier();*/
}