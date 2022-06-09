package org.skoman.ebankingbackend.entities;

import org.junit.jupiter.api.Test;
import org.skoman.ebankingbackend.enums.AccountCurrency;
import org.skoman.ebankingbackend.enums.AccountStatus;
import org.skoman.ebankingbackend.enums.OperationType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BankAccountTest {
    @Test
    void instantiate_BankAccount_Class(){
        Customer customer = Customer.builder()
                .name("Koman")
                .email("stefchris@gmail.com")
                .build();

        BankAccount bankAccount = new BankAccount();
        bankAccount.setCurrency(AccountCurrency.EUR);
        bankAccount.setBalance(1000);
        bankAccount.setStatus(AccountStatus.CREATED);
        bankAccount.setCustomer(customer);

        List<AccountOperation> accountOperations = new ArrayList<>(Arrays.asList(
                AccountOperation.builder()
                        .amount(5000)
                        .type(OperationType.CREDIT)
                        .operationDate(new Date())
                        .bankAccount(bankAccount)
                        .build(),
                AccountOperation.builder()
                        .amount(2000)
                        .type(OperationType.DEBIT)
                        .operationDate(new Date())
                        .bankAccount(bankAccount)
                        .build()
        ));

        bankAccount.setAccountOperations(accountOperations);

        assertEquals(bankAccount.getBalance(), 1000);
        assertEquals(bankAccount.getCurrency(), AccountCurrency.EUR);
        assertEquals(bankAccount.getStatus(), AccountStatus.CREATED);
        assertEquals(bankAccount.getCustomer().getName(), "Koman");

        assertEquals(bankAccount.getAccountOperations().size(), 2);
    }
}