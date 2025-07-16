package com.fabrick.banking_api.client;

import com.fabrick.banking_api.dto.BalanceResponce;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "fabrickClient", url = "${fabrick.api.base-url}")
public interface FabrickClient {

    @GetMapping("/api/gbs/banking/v4.0/accounts/{accountId}/balance")
    BalanceResponce getBalance(
            @RequestHeader("Auth-Schema") String authSchema,
            @RequestHeader("Api-Key") String apiKey,
            @PathVariable("accountId") Long accountId
    );
}
