package org.skoman.ebankingbackend.repositories;

import org.skoman.ebankingbackend.entities.AccountOperation;
import org.skoman.ebankingbackend.entities.BankAccount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountOperationRepository extends JpaRepository<AccountOperation, Long> {
}
