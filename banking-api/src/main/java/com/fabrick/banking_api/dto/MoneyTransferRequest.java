package com.fabrick.banking_api.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class MoneyTransferRequest {

    private Creditor creditor;
    private String executionDate;
    private String description;
    private BigDecimal amount;
    private String currency;

    @Data
    public static class Creditor {
        private String name;
        private Account account;
    }

    @Data
    public static class Account {
        private String accountCode;
    }
}
