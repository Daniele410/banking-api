package com.fabrick.banking_api.client;

import com.fabrick.banking_api.dto.BalanceResponce;
import com.fabrick.banking_api.dto.TransactionResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "fabrickClient", url = "${fabrick.api.base-url}")
public interface FabrickClient {

    @GetMapping("/api/gbs/banking/v4.0/accounts/{accountId}/balance")
    BalanceResponce getBalance(
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

}
