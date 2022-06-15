package org.skoman.ebankingbackend.controllers;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.skoman.ebankingbackend.dtos.CustomerDTO;
import org.skoman.ebankingbackend.dtos.CustomerSearchDTO;
import org.skoman.ebankingbackend.exceptions.CustomerNotFoundException;
import org.skoman.ebankingbackend.services.CustomerService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping( "/customers")
@AllArgsConstructor
@Slf4j
public class CustomerController {

    private final CustomerService customerService;

    @GetMapping("/search")
    public CustomerSearchDTO searchCustomers(@RequestParam(name = "page", defaultValue = "0") int page,
                                             @RequestParam(name = "size", defaultValue = "5") int size) {
        return customerService.searchCustomers(page, size);
    }

    @GetMapping("/{id}")
    public CustomerDTO getCustomer(@PathVariable(name = "id") Long customerId) throws CustomerNotFoundException {
        return customerService.getCustomer(customerId);
    }

    @PostMapping
    public CustomerDTO saveCustomer(@RequestBody CustomerDTO customerDTO){
        return customerService.saveCostumer(customerDTO);
    }

    @PutMapping("/{id}")
    public CustomerDTO updateCustomer(@PathVariable(name = "id") Long customerId, @RequestBody CustomerDTO customerDTO) throws CustomerNotFoundException {
        return customerService.updateCostumer(customerId, customerDTO);
    }

    @DeleteMapping("/{id}")
    public void deleteCustomer(@PathVariable(name = "id") Long customerId) throws CustomerNotFoundException {
        customerService.deleteCustomer(customerId);
    }
}
