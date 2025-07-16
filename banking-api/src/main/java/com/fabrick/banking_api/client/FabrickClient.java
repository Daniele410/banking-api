package com.fabrick.banking_api.client;

import com.fabrick.banking_api.dto.BalanceResponse;
import com.fabrick.banking_api.dto.MoneyTransferRequest;
import com.fabrick.banking_api.dto.MoneyTransferResponse;
import com.fabrick.banking_api.dto.TransactionResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(
        name = "fabrickClient",
        url = "${fabrick.api.base-url}"
)
public interface FabrickClient {

    @GetMapping("/api/gbs/banking/v4.0/accounts/{accountId}/balance")
    BalanceResponse getBalance(
            @RequestHeader("Auth-Schema") String authSchema,
            @RequestHeader("Api-Key") String apiKey,
            @PathVariable("accountId") Long accountId
    );

    @GetMapping("/api/gbs/banking/v4.0/accounts/{accountId}/transactions")
    TransactionResponse getTransactions(
            @RequestHeader("Auth-Schema") String authSchema,
            @RequestHeader("Api-Key") String apiKey,
            @PathVariable("accountId") Long accountId,
            @RequestParam("fromAccountingDate") String fromDate,
            @RequestParam("toAccountingDate") String toDate
    );

    @PostMapping("/api/gbs/banking/v4.0/accounts/{accountId}/payments/money-transfers")
    MoneyTransferResponse createMoneyTransfer(
            @RequestHeader("Auth-Schema") String authSchema,
            @RequestHeader("Api-Key") String apiKey,
            @RequestHeader("X-Time-Zone") String timeZone,
            @PathVariable("accountId") Long accountId,
            @RequestBody MoneyTransferRequest request
    );

}
