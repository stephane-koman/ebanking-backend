package org.skoman.ebankingbackend.services;

import org.skoman.ebankingbackend.dtos.*;
import org.skoman.ebankingbackend.entities.*;
import org.skoman.ebankingbackend.exceptions.BankAccountBalanceNotSufficientException;
import org.skoman.ebankingbackend.exceptions.BankAccountNotFoundException;
import org.skoman.ebankingbackend.exceptions.CustomerNotFoundException;

import java.util.List;

public interface BankAccountService {

    CurrentAccountDTO saveCurrentBankAccount(double initialBalance, double overDraft, Long customerId) throws CustomerNotFoundException;
    SavingAccountDTO saveSavingBankAccount(double initialBalance, double interestRate, Long customerId) throws CustomerNotFoundException;

    List<BankAccountDTO> bankAccountList();
    BankAccountDTO getBankAccount(String accountId) throws BankAccountNotFoundException;
    void debit(String accountId, double amount, String description) throws BankAccountNotFoundException, BankAccountBalanceNotSufficientException;
    void credit(String accountId, double amount, String description) throws BankAccountNotFoundException;
    void transfert(String accountIdSource, String accountIdDestination, double amount) throws BankAccountNotFoundException, BankAccountBalanceNotSufficientException;

    BankAccountSearchDTO searchBankAccounts(int page, int size);

    AccountHistoryDTO getAccountHistory(String accountId, int page, int size) throws BankAccountNotFoundException;

}
