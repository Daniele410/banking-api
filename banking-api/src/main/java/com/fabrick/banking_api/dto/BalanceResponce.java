package com.fabrick.banking_api.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class BalanceResponce {
    private String status;
    private BalancePayload payload;

    @Data
    public static class BalancePayload {
        private BigDecimal availableBalance;
        private BigDecimal balance;
        private BigDecimal currency;
        private LocalDateTime date;
    }


}
