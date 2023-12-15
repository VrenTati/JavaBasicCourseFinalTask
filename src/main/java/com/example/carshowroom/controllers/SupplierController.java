package com.example.carshowroom.controllers;

import com.example.carshowroom.data.Supplier;
import com.example.carshowroom.services.CarService;
import com.example.carshowroom.services.SupplierCarService;
import com.example.carshowroom.services.SupplierService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.List;
import java.util.Optional;

@Controller
@AllArgsConstructor
public class SupplierController {
    private final SupplierCarService supplierCarService;
    private final CarService carService;
    private final SupplierService supplierService;

    @PostMapping("/modify_suppliers/{id}")
    public String modifySuppliers(@RequestParam(name = "suppliers", defaultValue = "") List<Integer> supplierIds,
                                  @PathVariable int id){
        supplierCarService.updateSuppliersForCar(id, supplierIds);
        return "redirect:/car/car_info";

    }

    @GetMapping("/add_supplier")
    public String showAddSupplier(Model model){
        model.addAttribute("cars", carService.getCars());
        return "supplier/add_supplier";
    }

    @PostMapping("/add_supplier")
    public String addSupplier(
            @RequestParam("supplierName") String name,
            @RequestParam("supplierEmail") String email,
            @RequestParam("deliverDays") Integer deliverDays,
            @RequestParam("price") Double price,
            @RequestParam(name = "cars", defaultValue = "") List<Integer> carsIds
    ){
        Supplier supplier = new Supplier();
        supplier.setName(name);
        supplier.setEmail(email);
        supplier.setDeliverDays(deliverDays);
        supplier.setPrice(price);
        Supplier supplier1 = supplierService.saveSupplier(supplier);
        if(!carsIds.isEmpty()){
            supplierCarService.addCarSuppliersByCarsIds(carsIds, supplier1);
        }
        return "redirect:/all_suppliers";
    }

    @GetMapping("/all_suppliers")
    public String allSuppliers(Model model){
        model.addAttribute("suppliers", supplierService.getAllSuppliers());
        model.addAttribute("cars", carService.getCars());
        model.addAttribute("supplierCar", supplierCarService);
        return "supplier/suppliers";
    }

    @PostMapping("/supplier_info")
    public String modifySupplier(
            @RequestParam ("id") Integer suppId,
            @RequestParam("price") Double price,
            @RequestParam("deliverDays") Integer deliverDays,
            @RequestParam(name = "cars", defaultValue = "") List<Integer> carsIds
            ){
        Optional<Supplier> supplier = supplierService.getSupplierById(suppId);
        if (supplier.isPresent()) {
            supplier.get().setPrice(price);
            supplier.get().setDeliverDays(deliverDays);
            supplierService.updateSupplier(supplier.get(), carsIds);
        }
        return "redirect:/all_suppliers";
    }
}
