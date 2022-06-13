package org.skoman.ebankingbackend.controllers;

import lombok.AllArgsConstructor;
import org.skoman.ebankingbackend.dtos.AccountHistoryDTO;
import org.skoman.ebankingbackend.dtos.AccountOperationDTO;
import org.skoman.ebankingbackend.dtos.BankAccountDTO;
import org.skoman.ebankingbackend.enums.BankAccountType;
import org.skoman.ebankingbackend.exceptions.BankAccountNotFoundException;
import org.skoman.ebankingbackend.exceptions.CustomerNotFoundException;
import org.skoman.ebankingbackend.services.BankAccountService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/accounts")
public class BankAccountController {

    private final BankAccountService bankAccountService;

    @GetMapping("/{accountId}")
    public BankAccountDTO getBankAccount(@PathVariable(name = "accountId") String accountId) throws BankAccountNotFoundException {
        return bankAccountService.getBankAccount(accountId);
    }

    @GetMapping
    public List<BankAccountDTO> listAccounts(){
        return bankAccountService.bankAccountList();
    }

    @GetMapping("/{accountId}/operations")
    public List<AccountOperationDTO> getHistory(@PathVariable(name = "accountId") String accountId){
        return bankAccountService.getHistory(accountId);
    }

    @GetMapping("/{accountId}/pageOperations")
    public AccountHistoryDTO getAccountHistory(
            @PathVariable(name = "accountId") String accountId,
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "5") int size
    ) throws BankAccountNotFoundException {
        return bankAccountService.getAccountHistory(accountId, page, size);
    }
}
