package org.skoman.ebankingbackend.exceptions;

public class BankAccountBalanceNotSufficientException extends Exception {

    private static final String MESSAGE = "Balance of a Bank Account with ID: %s is not sufficient";

    public BankAccountBalanceNotSufficientException(String bankAccountId) {
        super(String.format(MESSAGE, bankAccountId));
    }

}
