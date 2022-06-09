package org.skoman.ebankingbackend.entities;

import org.junit.jupiter.api.Test;
import org.skoman.ebankingbackend.enums.AccountCurrency;
import org.skoman.ebankingbackend.enums.AccountStatus;
import org.skoman.ebankingbackend.enums.OperationType;

import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;

class AccountOperationTest {

    @Test
    void instantiate_AccountOperation_Class(){

        BankAccount bankAccount = BankAccount.builder()
                .balance(1000)
                .currency(AccountCurrency.EUR)
                .status(AccountStatus.CREATED)
                .createdAt(new Date())
                .build();

        AccountOperation accountOperation = AccountOperation.builder()
                .amount(2000)
                .type(OperationType.DEBIT)
                .operationDate(new Date())
                .bankAccount(bankAccount)
                .operationDate(new Date())
                .build();

        assertThat(accountOperation.getAmount()).isEqualTo(2000);
        assertThat(accountOperation.getType()).isEqualTo(OperationType.DEBIT);
        assertThat(accountOperation.getBankAccount().getStatus()).isEqualTo(AccountStatus.CREATED);
        assertThat(accountOperation.getBankAccount().getCustomer()).isNull();
    }
}