package com.fabrick.banking_api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MoneyTransferRequest {

    @NotNull
    private Creditor creditor;

    @NotBlank
    private String description;

    @NotNull
    private BigDecimal amount;

    @NotBlank
    private String currency;

    @NotBlank
    private String executionDate;

    @Data
    public static class Creditor {
        @NotBlank
        private String name;

        @NotNull
        private Account account;

        @Data
        public static class Account {
            @NotBlank
            private String accountCode;
        }
    }
}
