package com.bappi.customermanagement.model.enums;

import lombok.Getter;



public enum AccountType {
    SAVINGS_ACCOUNT("Savings Account"),
    CURRENT_ACCOUNT("Current Account"),
    PREMIUM_ACCOUNT("Premium Account");

    @Getter
    private final String message;

    AccountType(String message) {
        this.message = message;
    }
}
