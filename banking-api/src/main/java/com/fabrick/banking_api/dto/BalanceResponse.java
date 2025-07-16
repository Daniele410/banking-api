package com.fabrick.banking_api.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class BalanceResponse {
    private String status;
    private BalancePayload payload;

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class BalancePayload {
        private BigDecimal availableBalance;
        private BigDecimal balance;
        private String currency;
        private LocalDate date;
    }


}
