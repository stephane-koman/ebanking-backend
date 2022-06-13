package org.skoman.ebankingbackend;

import org.skoman.ebankingbackend.dtos.CustomerDTO;
import org.skoman.ebankingbackend.entities.*;
import org.skoman.ebankingbackend.enums.AccountCurrency;
import org.skoman.ebankingbackend.enums.AccountStatus;
import org.skoman.ebankingbackend.enums.OperationType;
import org.skoman.ebankingbackend.daos.AccountOperationDAO;
import org.skoman.ebankingbackend.daos.BankAccountDAO;
import org.skoman.ebankingbackend.daos.CustomerDAO;
import org.skoman.ebankingbackend.exceptions.BankAccountBalanceNotSufficientException;
import org.skoman.ebankingbackend.exceptions.BankAccountNotFoundException;
import org.skoman.ebankingbackend.exceptions.CustomerNotFoundException;
import org.skoman.ebankingbackend.services.BankAccountService;
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

    //@Bean
    CommandLineRunner start(CustomerDAO customerDAO, BankAccountDAO bankAccountDAO, AccountOperationDAO accountOperationDAO){
        return args -> {
            Stream.of("Stephane", "Chris", "Franck").forEach(name -> {
                Customer customer = Customer.builder()
                        .name(name)
                        .email((name + "@gmail.com").toLowerCase())
                        .build();
                customerDAO.save(customer);
            });

            customerDAO.findAll().forEach(cust -> {
                CurrentAccount currentAccount = new CurrentAccount();
                currentAccount.setId(UUID.randomUUID().toString());
                currentAccount.setCurrency(AccountCurrency.EUR);
                currentAccount.setStatus(AccountStatus.CREATED);
                currentAccount.setBalance(Math.random() * 90000);
                currentAccount.setCreatedAt(new Date());
                currentAccount.setCustomer(cust);
                currentAccount.setOverDraft(9000);
                bankAccountDAO.save(currentAccount);

                SavingAccount savingAccount = new SavingAccount();
                savingAccount.setId(UUID.randomUUID().toString());
                savingAccount.setCurrency(AccountCurrency.EUR);
                savingAccount.setStatus(AccountStatus.CREATED);
                savingAccount.setBalance(Math.random() * 90000);
                savingAccount.setCreatedAt(new Date());
                savingAccount.setCustomer(cust);
                savingAccount.setInterestRate(5.5);
                bankAccountDAO.save(savingAccount);
            });

            bankAccountDAO.findAll().forEach(acc -> {
                for (int i=0; i < 10; i++) {
                    AccountOperation accountOperation = AccountOperation.builder()
                            .operationDate(new Date())
                            .amount(Math.random() * 12000)
                            .type(Math.random() > 0.5 ? OperationType.DEBIT : OperationType.CREDIT)
                            .bankAccount(acc)
                            .build();

                    accountOperationDAO.save(accountOperation);
                }
            });
        };
    }

    @Bean
    CommandLineRunner commandLineRunner(BankAccountService bankAccountService){
        return args -> {
            Stream.of("Stephane", "Chris", "Franck").forEach(name -> {
                CustomerDTO customerDTO = new CustomerDTO();
                customerDTO.setName(name);
                customerDTO.setEmail((name + "@gmail.com").toLowerCase());

                bankAccountService.saveCostumer(customerDTO);
            });

            bankAccountService.listCostumers().forEach(customer -> {
                try {
                    bankAccountService.saveCurrentBankAccount(Math.random() * 90000, 9000, customer.getId());
                    bankAccountService.saveSavingBankAccount(Math.random() * 90000, 5.5, customer.getId());
                    bankAccountService.bankAccountList().forEach(bankAccount -> {
                        for (int i=0; i < 10; i++) {
                            if(Math.random() > 0.5){
                                try {
                                    bankAccountService.debit(bankAccount.getId(), Math.random() * 12000, "Debit account");
                                } catch (BankAccountNotFoundException | BankAccountBalanceNotSufficientException e) {
                                    e.printStackTrace();
                                }
                            }else {
                                try {
                                    bankAccountService.credit(bankAccount.getId(), Math.random() * 12000, "Credit account");
                                } catch (BankAccountNotFoundException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    });
                } catch (CustomerNotFoundException e) {
                    e.printStackTrace();
                }
            });
        };
    }
}
