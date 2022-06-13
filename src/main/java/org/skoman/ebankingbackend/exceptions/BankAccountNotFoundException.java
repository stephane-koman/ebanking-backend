package org.skoman.ebankingbackend.exceptions;

public class BankAccountNotFoundException extends Exception {
    private static final String MESSAGE = "Bank Account with ID: %s not found.";

    public BankAccountNotFoundException(String banckAccountId) {
        super(String.format(MESSAGE, banckAccountId));
    }
}
