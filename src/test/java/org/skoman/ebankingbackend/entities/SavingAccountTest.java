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

class SavingAccountTest {
    @Test
    void instantiate_SavingAccount_Class(){
        Customer customer = Customer.builder()
                .name("Koman")
                .email("stefchris@gmail.com")
                .build();

        SavingAccount savingAccount = new SavingAccount();
        savingAccount.setCurrency(AccountCurrency.EUR);
        savingAccount.setBalance(1000);
        savingAccount.setStatus(AccountStatus.CREATED);
        savingAccount.setInterestRate(3000);
        savingAccount.setCustomer(customer);

        List<AccountOperation> accountOperations = new ArrayList<>(Arrays.asList(
                AccountOperation.builder()
                        .amount(5000)
                        .type(OperationType.CREDIT)
                        .operationDate(new Date())
                        .bankAccount(savingAccount)
                        .build(),
                AccountOperation.builder()
                        .amount(2000)
                        .type(OperationType.DEBIT)
                        .operationDate(new Date())
                        .bankAccount(savingAccount)
                        .build()
        ));

        savingAccount.setAccountOperations(accountOperations);

        assertEquals(savingAccount.getInterestRate(), 3000);
        assertEquals(savingAccount.getBalance(), 1000);
        assertEquals(savingAccount.getCurrency(), AccountCurrency.EUR);
        assertEquals(savingAccount.getStatus(), AccountStatus.CREATED);
        assertEquals(savingAccount.getCustomer().getName(), "Koman");

        assertEquals(savingAccount.getAccountOperations().size(), 2);
    }
}