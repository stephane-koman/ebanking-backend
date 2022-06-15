package org.skoman.ebankingbackend.services;

import org.skoman.ebankingbackend.dtos.CustomerDTO;
import org.skoman.ebankingbackend.dtos.CustomerSearchDTO;
import org.skoman.ebankingbackend.exceptions.CustomerNotFoundException;

public interface CustomerService {
    CustomerDTO getCustomer(Long customerId) throws CustomerNotFoundException;
    CustomerDTO saveCostumer(CustomerDTO customerDTO);
    CustomerDTO updateCostumer(Long customerId, CustomerDTO customerDTO) throws CustomerNotFoundException;
    void deleteCustomer(Long customerId) throws CustomerNotFoundException;
    CustomerSearchDTO searchCustomers(int page, int size);
}
