package org.skoman.ebankingbackend;

import org.skoman.ebankingbackend.entities.CurrentAccount;
import org.skoman.ebankingbackend.entities.Customer;
import org.skoman.ebankingbackend.entities.SavingAccount;
import org.skoman.ebankingbackend.enums.AccountCurrency;
import org.skoman.ebankingbackend.enums.AccountStatus;
import org.skoman.ebankingbackend.repositories.AccountOperationRepository;
import org.skoman.ebankingbackend.repositories.BankAccountRepository;
import org.skoman.ebankingbackend.repositories.CustomerRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Date;
import java.util.UUID;
import java.util.stream.Stream;

@SpringBootApplication
public class EbankingBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(EbankingBackendApplication.class, args);
    }

    @Bean
    CommandLineRunner start(CustomerRepository customerRepository, BankAccountRepository bankAccountRepository, AccountOperationRepository accountOperationRepository){
        return args -> {
            Stream.of("Stephane", "Chris", "Franck").forEach(name -> {
                Customer customer = Customer.builder()
                        .name(name)
                        .email((name + "@gmail.com").toLowerCase())
                        .build();
                customerRepository.save(customer);
            });

            customerRepository.findAll().forEach(cust -> {
                CurrentAccount currentAccount = new CurrentAccount();
                currentAccount.setId(UUID.randomUUID().toString());
                currentAccount.setCurrency(AccountCurrency.EUR);
                currentAccount.setStatus(AccountStatus.CREATED);
                currentAccount.setBalance(Math.random() * 90000);
                currentAccount.setCreatedAt(new Date());
                currentAccount.setCustomer(cust);
                currentAccount.setOverDraft(9000);
                bankAccountRepository.save(currentAccount);

                SavingAccount savingAccount = new SavingAccount();
                savingAccount.setId(UUID.randomUUID().toString());
                savingAccount.setCurrency(AccountCurrency.EUR);
                savingAccount.setStatus(AccountStatus.CREATED);
                savingAccount.setBalance(Math.random() * 90000);
                savingAccount.setCreatedAt(new Date());
                savingAccount.setCustomer(cust);
                savingAccount.setInterestRate(5.5);
                bankAccountRepository.save(savingAccount);
            });
        };
    }
}
