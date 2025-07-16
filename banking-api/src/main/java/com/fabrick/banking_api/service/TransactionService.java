package com.fabrick.banking_api.service;

import com.fabrick.banking_api.client.FabrickClient;
import com.fabrick.banking_api.config.FabrickProperties;
import com.fabrick.banking_api.dto.TransactionResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TransactionService {

    private final FabrickClient fabrickClient;
    private final FabrickProperties fabrickProperties;

    @Value("${fabrick.account-id}")
    private Long accountId;

    public TransactionResponse getTransactions(String fromDate, String toDate) {
        return fabrickClient.getTransactions(
                fabrickProperties.getAuthSchema(),
                fabrickProperties.getKey(),
                accountId,
                fromDate,
                toDate
        );
    }
}
