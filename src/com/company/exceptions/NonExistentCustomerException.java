package com.company.exceptions;

public class NonExistentCustomerException extends NonExistentEntityException {

    public NonExistentCustomerException() {
        super("Покупатель не существует");
    }
}
