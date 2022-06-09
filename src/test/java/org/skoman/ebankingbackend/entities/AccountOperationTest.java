package org.skoman.ebankingbackend.entities;

import org.junit.jupiter.api.Test;
import org.skoman.ebankingbackend.enums.AccountCurrency;
import org.skoman.ebankingbackend.enums.AccountStatus;
import org.skoman.ebankingbackend.enums.OperationType;

import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;

class AccountOperationTest {

    @Test
    void instantiate_AccountOperation_Class_CurrentAccount(){

        CurrentAccount currentAccount = CurrentAccount.builder()
                .balance(1000)
                .currency(AccountCurrency.EUR)
                .status(AccountStatus.CREATED)
                .createdAt(new Date())
                .overDraft(3000)
                .build();

        AccountOperation accountOperation = AccountOperation.builder()
                .amount(2000)
                .type(OperationType.DEBIT)
                .operationDate(new Date())
                .bankAccount(currentAccount)
                .operationDate(new Date())
                .build();

        assertThat(accountOperation.getAmount()).isEqualTo(2000);
        assertThat(accountOperation.getType()).isEqualTo(OperationType.DEBIT);
        assertThat(accountOperation.getBankAccount().getStatus()).isEqualTo(AccountStatus.CREATED);
        assertThat(((CurrentAccount) accountOperation.getBankAccount()).getOverDraft()).isEqualTo(3000);
        assertThat(accountOperation.getBankAccount().getCustomer()).isNull();
    }

    @Test
    void instantiate_AccountOperation_Class_SavingAccount(){

        SavingAccount savingAccount = SavingAccount.builder()
                .balance(1000)
                .currency(AccountCurrency.EUR)
                .status(AccountStatus.CREATED)
                .createdAt(new Date())
                .interestRate(5.8)
                .build();

        AccountOperation accountOperation = AccountOperation.builder()
                .amount(2000)
                .type(OperationType.DEBIT)
                .operationDate(new Date())
                .bankAccount(savingAccount)
                .operationDate(new Date())
                .build();

        assertThat(accountOperation.getAmount()).isEqualTo(2000);
        assertThat(accountOperation.getType()).isEqualTo(OperationType.DEBIT);
        assertThat(accountOperation.getBankAccount().getStatus()).isEqualTo(AccountStatus.CREATED);
        assertThat(((SavingAccount) accountOperation.getBankAccount()).getInterestRate()).isEqualTo(5.8);
        assertThat(accountOperation.getBankAccount().getCustomer()).isNull();
    }
}