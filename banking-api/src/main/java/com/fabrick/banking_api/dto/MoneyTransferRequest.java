package com.fabrick.banking_api.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MoneyTransferRequest {

    @Valid
    @NotNull(message = "Creditor is required")
    private Creditor creditor;

    @NotBlank(message = "Description is required")
    @Size(max = 140)
    private String description;

    @NotNull(message = "Amount is required")
    @DecimalMin(value = "0.01")
    private BigDecimal amount;

    @NotBlank(message = "Currency is required")
    private String currency;

    @NotBlank(message = "Execution date is required")
    // could use @Pattern(…) or @JsonFormat for yyyy-MM-dd
    private String executionDate;

    /* ---------- nested class ---------- */
    @Data
    public static class Creditor {
        @NotBlank(message = "Creditor name is required")
        private String name;

        @Valid
        @NotNull(message = "Creditor account is required")
        private Account account;


        @Data
        public static class Account {
            @NotBlank(message = "accountCode (IBAN) is required")
            private String accountCode;
        }
    }
}
