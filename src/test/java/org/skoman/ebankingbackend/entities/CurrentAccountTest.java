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

/**
 * Test unitaire de {@link CurrentAccount}
 */
class CurrentAccountTest {
    @Test
    void instantiate_CurrentAccount_Class(){
        Customer customer = Customer.builder()
                .name("Koman")
                .email("stefchris@gmail.com")
                .build();

        CurrentAccount currentAccount = new CurrentAccount();
        currentAccount.setCurrency(AccountCurrency.EUR);
        currentAccount.setBalance(1000);
        currentAccount.setStatus(AccountStatus.CREATED);
        currentAccount.setOverDraft(2000);
        currentAccount.setCustomer(customer);

        List<AccountOperation> accountOperations = new ArrayList<>(Arrays.asList(
                AccountOperation.builder()
                        .amount(5000)
                        .type(OperationType.CREDIT)
                        .operationDate(new Date())
                        .bankAccount(currentAccount)
                        .build(),
                AccountOperation.builder()
                        .amount(2000)
                        .type(OperationType.DEBIT)
                        .operationDate(new Date())
                        .bankAccount(currentAccount)
                        .build()
        ));

        currentAccount.setAccountOperations(accountOperations);

        assertEquals(currentAccount.getOverDraft(), 2000);
        assertEquals(currentAccount.getBalance(), 1000);
        assertEquals(currentAccount.getCurrency(), AccountCurrency.EUR);
        assertEquals(currentAccount.getStatus(), AccountStatus.CREATED);
        assertEquals(currentAccount.getCustomer().getName(), "Koman");

        assertEquals(currentAccount.getAccountOperations().size(), 2);
    }
}