package org.skoman.ebankingbackend.entities;

import org.junit.jupiter.api.Test;
import org.skoman.ebankingbackend.enums.AccountCurrency;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test unitaire de {@link Customer}
 */
class CustomerTest {
    @Test
    void instantiate_Customer_Class(){
        Customer customer = Customer.builder()
                .name("Koman")
                .email("stefchris@gmail.com")
                .build();

        List<BankAccount> bankAccounts = new ArrayList<>();

        CurrentAccount currentAccount = new CurrentAccount();
        SavingAccount savingAccount = new SavingAccount();

        currentAccount.setCustomer(customer);
        currentAccount.setCurrency(AccountCurrency.EUR);
        currentAccount.setBalance(2000);
        currentAccount.setOverDraft(1000);

        bankAccounts.add(currentAccount);

        savingAccount.setCustomer(customer);
        savingAccount.setCurrency(AccountCurrency.EUR);
        savingAccount.setBalance(2000);
        savingAccount.setInterestRate(3000);

        bankAccounts.add(savingAccount);

        customer.setBankAccounts(bankAccounts);

        assertEquals(customer.getName(), "Koman");
        assertEquals(customer.getEmail(), "stefchris@gmail.com");
        assertEquals(customer.getBankAccounts().size(), 2);
    }
}