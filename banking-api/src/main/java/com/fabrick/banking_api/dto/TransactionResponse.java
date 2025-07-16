package com.fabrick.banking_api.dto;

import lombok.Data;

import java.util.List;

@Data
public class TransactionResponse {

    private String status;
    private TransactionPayload payload;

    @Data
    public static class TransactionPayload {
        private List<Transaction> list;
    }

    @Data
    public static class Transaction {
        private String transactionId;
        private String operationId;
        private String accountingDate;
        private String valueDate;
        private TransactionType type;
        private String amount;
        private String currency;
        private String description;
    }

    @Data
    public static class TransactionType {
        private String enumeration;
        private String value;
    }

}
