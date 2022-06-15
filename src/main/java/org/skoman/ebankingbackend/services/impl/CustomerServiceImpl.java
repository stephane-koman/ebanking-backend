package org.skoman.ebankingbackend.services.impl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.skoman.ebankingbackend.daos.CustomerDAO;
import org.skoman.ebankingbackend.dtos.*;
import org.skoman.ebankingbackend.entities.*;
import org.skoman.ebankingbackend.exceptions.CustomerNotFoundException;
import org.skoman.ebankingbackend.mappers.CustomerMapper;
import org.skoman.ebankingbackend.services.CustomerService;
import org.skoman.ebankingbackend.utils.MappeDTOData;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@AllArgsConstructor
@Slf4j
public class CustomerServiceImpl implements CustomerService {

    private final CustomerDAO customerDAO;

    private final CustomerMapper customerMapper;

    @Override
    public CustomerDTO getCustomer(Long customerId) throws CustomerNotFoundException {
        Customer customer = customerDAO.findById(customerId).orElseThrow(() ->new CustomerNotFoundException(customerId));
        return customerMapper.fromCustomer(customer);
    }

    @Override
    public CustomerDTO saveCostumer(CustomerDTO customerDTO) {
        Customer customer = customerDAO.save(customerMapper.fromCustomerDTO(customerDTO));
        return customerMapper.fromCustomer(customer);
    }

    @Override
    public CustomerDTO updateCostumer(Long customerId, CustomerDTO customerDTO) throws CustomerNotFoundException {

        if(!customerDAO.existsById(customerId)) throw new CustomerNotFoundException(customerId);

        customerDTO.setId(customerId);
        Customer customerUpdated = customerDAO.save(customerMapper.fromCustomerDTO(customerDTO));
        return customerMapper.fromCustomer(customerUpdated);
    }

    @Override
    public void deleteCustomer(Long customerId) throws CustomerNotFoundException {
        if(!customerDAO.existsById(customerId)) throw new CustomerNotFoundException(customerId);
        customerDAO.deleteById(customerId);
    }

    @Override
    public CustomerSearchDTO searchCustomers(int page, int size) {
        Page<Customer> customers = customerDAO.findCustomersBy(PageRequest.of(page, size));
        List<CustomerDTO> customerDTOS = customers.getContent().stream().map(customerMapper::fromCustomer).collect(Collectors.toList());

        CustomerSearchDTO customerSearchDTO = new CustomerSearchDTO();
        customerSearchDTO.setCustomerDTOS(customerDTOS);
        MappeDTOData.mappePagination(customerSearchDTO, page, size, customers.getTotalPages());
        return customerSearchDTO;
    }
}
