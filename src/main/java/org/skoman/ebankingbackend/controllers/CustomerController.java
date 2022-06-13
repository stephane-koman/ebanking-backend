package org.skoman.ebankingbackend.controllers;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.skoman.ebankingbackend.dtos.CustomerDTO;
import org.skoman.ebankingbackend.entities.Customer;
import org.skoman.ebankingbackend.exceptions.CustomerNotFoundException;
import org.skoman.ebankingbackend.services.BankAccountService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping( "/customers")
@AllArgsConstructor
@Slf4j
public class CustomerController {

    private final BankAccountService bankAccountService;

    @GetMapping("")
    public List<CustomerDTO> getCustomers(){
        return bankAccountService.listCostumers();
    }

    @GetMapping("/{id}")
    public CustomerDTO getCustomer(@PathVariable(name = "id") Long customerId) throws CustomerNotFoundException {
        return bankAccountService.getCustomer(customerId);
    }

    @PostMapping
    public CustomerDTO saveCustomer(@RequestBody CustomerDTO customerDTO){
        return bankAccountService.saveCostumer(customerDTO);
    }

    @PutMapping("/{id}")
    public CustomerDTO updateCustomer(@PathVariable(name = "id") Long customerId, @RequestBody CustomerDTO customerDTO) throws CustomerNotFoundException {
        return bankAccountService.updateCostumer(customerId, customerDTO);
    }

    @DeleteMapping("/{id}")
    public void deleteCustomer(@PathVariable(name = "id") Long customerId) throws CustomerNotFoundException {
        bankAccountService.deleteCustomer(customerId);
    }
}
