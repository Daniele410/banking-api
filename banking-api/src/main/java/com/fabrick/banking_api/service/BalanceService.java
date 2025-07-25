package com.fabrick.banking_api.service;

import com.fabrick.banking_api.client.FabrickClient;
import com.fabrick.banking_api.config.FabrickProperties;
import com.fabrick.banking_api.dto.BalanceResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BalanceService {

    private final FabrickClient fabrickClient;
    private final FabrickProperties fabrickProperties;

    @Value("${fabrick.account-id}")
    private Long accountId;

    public BalanceResponse getBalance() {
        return fabrickClient.getBalance(
                fabrickProperties.getAuthSchema(),
                fabrickProperties.getKey(),
                accountId
        );
    }

}
