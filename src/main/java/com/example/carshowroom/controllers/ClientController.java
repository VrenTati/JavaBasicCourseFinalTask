package com.example.carshowroom.controllers;

import com.example.carshowroom.data.Client;
import com.example.carshowroom.services.ClientService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import java.math.BigDecimal;
import java.util.Optional;

@Controller
@AllArgsConstructor
public class ClientController {

    private final ClientService clientService;

    @GetMapping("/clients")
    public String getClients(Model model) {
        model.addAttribute("clients", clientService.findClientsWithoutSuppliers());
        return "client/clients";
    }

    @PostMapping("/client_info")
    public String modifyCharacteristics(
            @RequestParam(name = "model", required = false, defaultValue = "") String carModel,
            @RequestParam(name = "brand", required = false, defaultValue = "") String brand,
            @RequestParam(name = "carType", required = false, defaultValue = "") String carType,
            @RequestParam(name = "year", required = false) Integer year,
            @RequestParam(name = "fuelType", required = false, defaultValue = "") String fuelType,
            @RequestParam(name = "transmissionType", required = false, defaultValue = "") String transmissionType,
            @RequestParam(name = "fuelConsumption", required = false) BigDecimal fuelConsumption,
            @RequestParam(name = "price", required = false) BigDecimal maxPrice,
            @RequestParam(name = "used", defaultValue = "false") Boolean used,
            @RequestParam("id") int id) {

        Optional<Client> client = clientService.getClient(id);
        if (client.isPresent()) {
            Client client1 = client.get();
            clientService.setClientValues(
                    carModel,
                    brand,
                    carType,
                    fuelType,
                    transmissionType,
                    year,
                    fuelConsumption,
                    maxPrice,
                    used,
                    client1);
            clientService.updateClient(client1);
        }

        return "redirect:/client_info/" + id;
    }

    @GetMapping("/client_info/{id}")
    public String showSuccessModifyPage(@PathVariable int id, Model model) {
        Optional<Client> client = clientService.getClient(id);
        client.ifPresent(value -> model.addAttribute("client", value));
        return "client/client_info";
    }

    @GetMapping("/add_client")
    public String addClient(Model model) {
        return "client/add_client";
    }

    @PostMapping("/add_client")
    public String addClient(
            @RequestParam("name") String name,
            @RequestParam("email") String email,
            @RequestParam(name = "model", required = false, defaultValue = "") String carModel,
            @RequestParam(name = "brand", required = false, defaultValue = "") String brand,
            @RequestParam(name = "carType", required = false, defaultValue = "") String carType,
            @RequestParam(name = "year", required = false) Integer year,
            @RequestParam(name = "fuelType", required = false, defaultValue = "") String fuelType,
            @RequestParam(name = "transmissionType", required = false, defaultValue = "") String transmissionType,
            @RequestParam(name = "fuelConsumption", required = false) BigDecimal fuelConsumption,
            @RequestParam(name = "price", required = false) BigDecimal maxPrice,
            @RequestParam(value = "used", defaultValue = "false") boolean used) {

        Client newClient = new Client();
        String[] cl_name = name.split(" ");
        if (cl_name.length == 2){
            newClient.setName(cl_name[0]);
            newClient.setLastname(cl_name[1]);
        }

        newClient.setEmail(email);
        clientService.setClientValues(
                carModel,
                brand,
                carType,
                fuelType,
                transmissionType,
                year,
                fuelConsumption,
                maxPrice,
                used,
                newClient);
        Client c = clientService.addClient(newClient);

        return "redirect:/client_info/" + c.getId();
    }

    /*private void setClientValues(String carModel,
                                 String brand,
                                 String carType,
                                 String fuelType,
                                 String transmissionType,
                                 Integer year,
                                 BigDecimal fuelConsumption,
                                 BigDecimal maxPrice,
                                 boolean used,
                                 Client newClient) {
        if (carModel.isEmpty())
            newClient.setModel(null);
        else
            newClient.setModel(carModel);
        if (brand.isEmpty())
            newClient.setBrand(null);
        else
            newClient.setBrand(brand);
        if (carType.isEmpty())
            newClient.setCarType(null);
        else
            newClient.setCarType(carType);
        if (fuelType.isEmpty())
            newClient.setFuelType(null);
        else
            newClient.setFuelType(fuelType);
        if (transmissionType.isEmpty())
            newClient.setTransmissionType(null);
        else
            newClient.setTransmissionType(transmissionType);
        newClient.setYear(year);
        newClient.setMaxFuelConsumption(fuelConsumption);
        newClient.setMaxPrice(maxPrice);
        newClient.setUsed(used);
    }*/

    @GetMapping("/delete_client/{id}")
    public String deleteClient(@PathVariable int id){
        clientService.deleteById(id);
        return "redirect:/clients";
    }


}
