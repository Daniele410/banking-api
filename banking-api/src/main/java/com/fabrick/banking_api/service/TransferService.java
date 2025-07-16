package com.fabrick.banking_api.service;

import com.fabrick.banking_api.client.FabrickClient;
import com.fabrick.banking_api.config.FabrickProperties;
import com.fabrick.banking_api.dto.MoneyTransferRequest;
import com.fabrick.banking_api.dto.MoneyTransferResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TransferService {

    private final FabrickClient fabrickClient;
    private final FabrickProperties fabrickProperties;

    @Value("${fabrick.account-id}")
    private Long accountId;

    public MoneyTransferResponse createTransfer(MoneyTransferRequest request) {
        return fabrickClient.createMoneyTransfer(
                fabrickProperties.getAuthSchema(),
                fabrickProperties.getKey(),
                accountId,
                request
        );
    }

}
