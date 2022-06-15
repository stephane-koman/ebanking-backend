package org.skoman.ebankingbackend.daos;

import org.skoman.ebankingbackend.entities.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerDAO extends JpaRepository<Customer, Long> {
    Page<Customer> findCustomersBy(Pageable pageable);
}
