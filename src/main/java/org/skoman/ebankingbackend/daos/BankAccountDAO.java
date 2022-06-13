package org.skoman.ebankingbackend.daos;

import org.skoman.ebankingbackend.entities.BankAccount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BankAccountDAO extends JpaRepository<BankAccount, String> {
}
