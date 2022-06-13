package org.skoman.ebankingbackend.services.impl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.skoman.ebankingbackend.dtos.*;
import org.skoman.ebankingbackend.entities.*;
import org.skoman.ebankingbackend.enums.AccountCurrency;
import org.skoman.ebankingbackend.enums.AccountStatus;
import org.skoman.ebankingbackend.enums.OperationType;
import org.skoman.ebankingbackend.exceptions.BankAccountBalanceNotSufficientException;
import org.skoman.ebankingbackend.exceptions.BankAccountNotFoundException;
import org.skoman.ebankingbackend.exceptions.CustomerNotFoundException;
import org.skoman.ebankingbackend.daos.AccountOperationDAO;
import org.skoman.ebankingbackend.daos.BankAccountDAO;
import org.skoman.ebankingbackend.daos.CustomerDAO;
import org.skoman.ebankingbackend.mappers.BankAccountMapper;
import org.skoman.ebankingbackend.services.BankAccountService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
@AllArgsConstructor
@Slf4j
public class BankAccountServiceImpl implements BankAccountService {

    private final CustomerDAO customerDAO;

    private final BankAccountDAO bankAccountDAO;

    private final AccountOperationDAO accountOperationDAO;

    private final BankAccountMapper bankAccountMapper;

    @Override
    public CustomerDTO getCustomer(Long customerId) throws CustomerNotFoundException {
        Customer customer = customerDAO.findById(customerId).orElseThrow(() ->new CustomerNotFoundException(customerId));
        return bankAccountMapper.fromCustomer(customer);
    }

    @Override
    public CustomerDTO saveCostumer(CustomerDTO customerDTO) {
        Customer customer = customerDAO.save(bankAccountMapper.fromCustomerDTO(customerDTO));
        return bankAccountMapper.fromCustomer(customer);
    }

    @Override
    public CustomerDTO updateCostumer(Long customerId, CustomerDTO customerDTO) throws CustomerNotFoundException {

        if(!customerDAO.existsById(customerId)) throw new CustomerNotFoundException(customerId);

        customerDTO.setId(customerId);
        Customer customerUpdated = customerDAO.save(bankAccountMapper.fromCustomerDTO(customerDTO));
        return bankAccountMapper.fromCustomer(customerUpdated);
    }

    @Override
    public void deleteCustomer(Long customerId) throws CustomerNotFoundException {
        if(!customerDAO.existsById(customerId)) throw new CustomerNotFoundException(customerId);
        customerDAO.deleteById(customerId);
    }

    @Override
    public List<CustomerDTO> listCostumers() {
        return customerDAO.findAll().stream().map(bankAccountMapper::fromCustomer).collect(Collectors.toList());
    }

    private <T extends BankAccount> void mappeBankAccount(T bankAccount, double initialBalance, Long customerId) throws CustomerNotFoundException {
        Customer customer = customerDAO.findById(customerId).orElse(null);
        if(customer == null)
            throw new CustomerNotFoundException(customerId);

        bankAccount.setId(UUID.randomUUID().toString());
        bankAccount.setBalance(initialBalance);
        bankAccount.setCreatedAt(new Date());
        bankAccount.setCustomer(customer);
        bankAccount.setStatus(AccountStatus.CREATED);
        bankAccount.setCurrency(AccountCurrency.EUR);
    };

    @Override
    public CurrentAccountDTO saveCurrentBankAccount(double initialBalance, double overDraft, Long customerId) throws CustomerNotFoundException {
        CurrentAccount currentAccount = new CurrentAccount();
        mappeBankAccount(currentAccount, initialBalance, customerId);
        currentAccount.setOverDraft(overDraft);
        CurrentAccount savedCurrentAccount = bankAccountDAO.save(currentAccount);
        return bankAccountMapper.fromCurrentAccount(savedCurrentAccount);
    }

    @Override
    public SavingAccountDTO saveSavingBankAccount(double initialBalance, double interestRate, Long customerId) throws CustomerNotFoundException {
        SavingAccount savingAccount = new SavingAccount();
        mappeBankAccount(savingAccount, initialBalance, customerId);
        savingAccount.setInterestRate(interestRate);
        SavingAccount savedSavingAccount = bankAccountDAO.save(savingAccount);
        return bankAccountMapper.fromSavingAccount(savedSavingAccount);
    }

    @Override
    public List<BankAccountDTO> bankAccountList() {
        return bankAccountDAO.findAll().stream().map(bankAccount -> {
            if(bankAccount instanceof SavingAccount){
                SavingAccount savingAccount = (SavingAccount) bankAccount;
                return bankAccountMapper.fromSavingAccount(savingAccount);
            }

            CurrentAccount currentAccount = (CurrentAccount) bankAccount;
            return bankAccountMapper.fromCurrentAccount(currentAccount);
        }).collect(Collectors.toList());
    }

    @Override
    public BankAccountDTO getBankAccount(String accountId) throws BankAccountNotFoundException {
        BankAccount bankAccount = bankAccountDAO.findById(accountId)
                .orElseThrow(() ->new BankAccountNotFoundException(accountId));

        if (bankAccount instanceof SavingAccount) {
            SavingAccount savingAccount = (SavingAccount) bankAccount;
            return bankAccountMapper.fromSavingAccount(savingAccount);
        }

        CurrentAccount currentAccount = (CurrentAccount) bankAccount;
        return bankAccountMapper.fromCurrentAccount(currentAccount);
    }

    private void mappeAccountOperation(AccountOperation accountOperation, BankAccount bankAccount, double amount, String description, OperationType type){
        accountOperation.setBankAccount(bankAccount);
        accountOperation.setAmount(amount);
        accountOperation.setOperationDate(new Date());
        accountOperation.setType(type);
        accountOperation.setDescription(description);
    }

    private BankAccount getBankAccountEntityById(String accountId) throws BankAccountNotFoundException {
        return bankAccountDAO.findById(accountId)
                .orElseThrow(() ->new BankAccountNotFoundException(accountId));
    }

    @Override
    public void debit(String accountId, double amount, String description) throws BankAccountNotFoundException, BankAccountBalanceNotSufficientException {
        BankAccount bankAccount = getBankAccountEntityById(accountId);

        if(bankAccount.getBalance() < amount) throw new BankAccountBalanceNotSufficientException(accountId);

        AccountOperation accountOperation = new AccountOperation();
        mappeAccountOperation(accountOperation, bankAccount, amount, description, OperationType.DEBIT);
        accountOperationDAO.save(accountOperation);

        bankAccount.setBalance(bankAccount.getBalance() - amount);
        bankAccountDAO.save(bankAccount);
    }

    @Override
    public void credit(String accountId, double amount, String description) throws BankAccountNotFoundException {
        BankAccount bankAccount = getBankAccountEntityById(accountId);

        AccountOperation accountOperation = new AccountOperation();
        mappeAccountOperation(accountOperation, bankAccount, amount, description, OperationType.CREDIT);
        accountOperationDAO.save(accountOperation);

        bankAccount.setBalance(bankAccount.getBalance() + amount);
        bankAccountDAO.save(bankAccount);
    }

    @Override
    public void transfert(String accountIdSource, String accountIdDestination, double amount) throws BankAccountNotFoundException, BankAccountBalanceNotSufficientException {
        debit(accountIdSource, amount, "Transfert To Bank Account with ID: " + accountIdDestination);
        credit(accountIdDestination, amount, "Transfert from Bank Account with ID: " + accountIdSource);
    }

    @Override
    public List<AccountOperationDTO> getHistory(String accountId) {
        List<AccountOperation> accountOperations = accountOperationDAO.findByBankAccountId(accountId);
        return accountOperations.stream().map(bankAccountMapper::fromAccountOperation).collect(Collectors.toList());
    }

    @Override
    public AccountHistoryDTO getAccountHistory(String accountId, int page, int size) throws BankAccountNotFoundException {
        BankAccount bankAccount = getBankAccountEntityById(accountId);
        Page<AccountOperation> accountOperations = accountOperationDAO.findByBankAccountId(accountId, PageRequest.of(page, size));
        List<AccountOperationDTO> accountOperationDTOS = accountOperations.getContent().stream().map(bankAccountMapper::fromAccountOperation).collect(Collectors.toList());

        AccountHistoryDTO accountHistoryDTO = new AccountHistoryDTO();
        accountHistoryDTO.setAccountId(accountId);
        accountHistoryDTO.setBalance(bankAccount.getBalance());
        accountHistoryDTO.setAccountOperationDTOS(accountOperationDTOS);
        accountHistoryDTO.setPageSize(size);
        accountHistoryDTO.setCurrentPage(page);
        accountHistoryDTO.setTotalPages(accountOperations.getTotalPages());

        return accountHistoryDTO;
    }
}
