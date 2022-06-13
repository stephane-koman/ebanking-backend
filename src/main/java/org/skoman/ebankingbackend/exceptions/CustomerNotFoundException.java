package org.skoman.ebankingbackend.exceptions;

public class CustomerNotFoundException extends Exception {

    private static final String MESSAGE = "Costumer with ID: %d not found.";

    public CustomerNotFoundException(String message) {
        super(message);
    }

    public CustomerNotFoundException(Long customerId) {
        super(String.format(MESSAGE, customerId));
    }
}
