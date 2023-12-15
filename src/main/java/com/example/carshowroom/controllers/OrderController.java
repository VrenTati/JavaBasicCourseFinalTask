package com.example.carshowroom.controllers;

import com.example.carshowroom.data.Car;
import com.example.carshowroom.data.Client;
import com.example.carshowroom.data.ClientCarSupplier;
import com.example.carshowroom.services.CarService;
import com.example.carshowroom.services.ClientCarSupplierService;
import com.example.carshowroom.services.ClientService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@AllArgsConstructor
public class OrderController {

    private final ClientCarSupplierService clientCarSupplierService;

    @PostMapping("/submit_order")
    public String submitOrder(
            @RequestParam("clientId") Integer clientId,
            @RequestParam("supplierId") Integer supplierId,
            @RequestParam("carId") Integer carId,
            @RequestParam("receipt") String receipt) {
        System.out.println(supplierId);
        ClientCarSupplier order = clientCarSupplierService.addOrder(clientId, carId, supplierId, receipt);
        return "redirect:/order_info/" + order.getId();
    }

    @GetMapping("/order_info/{id}")
    public String orderInfo(@PathVariable int id, Model model){
        Optional<ClientCarSupplier> order = clientCarSupplierService.getOrderById(id);
        order.ifPresent(clientCarSupplier -> model.addAttribute("order", clientCarSupplier));
        return "order/order_info";
    }

    @GetMapping("/purchased_cars")
    public String showOrder(Model model){
        List<ClientCarSupplier> orders = clientCarSupplierService.getAllOrders();
        List<Client> clients =new ArrayList<>();
        List<Car> cars = new ArrayList<>();
        for (ClientCarSupplier order : orders){
            clients.add(order.getClient());
            cars.add(order.getCar());
        }

        model.addAttribute("clients", clients);
        model.addAttribute("cars", cars);
        model.addAttribute("orders", orders);
        return "order/orders";
    }

}
