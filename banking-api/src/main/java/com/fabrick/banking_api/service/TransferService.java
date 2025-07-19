package com.fabrick.banking_api.service;

import com.fabrick.banking_api.client.FabrickClient;
import com.fabrick.banking_api.config.FabrickProperties;
import com.fabrick.banking_api.dto.MoneyTransferRequest;
import com.fabrick.banking_api.dto.MoneyTransferResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import feign.FeignException;
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

    private final ObjectMapper objectMapper;

    public MoneyTransferResponse createTransfer(MoneyTransferRequest request) {
        try {
            return fabrickClient.createMoneyTransfer(
                    fabrickProperties.getAuthSchema(),
                    fabrickProperties.getKey(),
                    "Europe/Rome",
                    accountId,
                    request
            );
        } catch (FeignException.BadRequest ex) {
            return parseErrorResponse(ex);
        }
    }

    private MoneyTransferResponse parseErrorResponse(FeignException.BadRequest ex) {
        try {
            return objectMapper.readValue(ex.contentUTF8(), MoneyTransferResponse.class);
        } catch (Exception e) {
            MoneyTransferResponse fallback = new MoneyTransferResponse();
            fallback.setStatus("KO");

            MoneyTransferResponse.Error err = new MoneyTransferResponse.Error();
            err.setCode("REQ500");
            err.setDescription("Errore tecnico generico");

            fallback.setErrors(new MoneyTransferResponse.Error[]{err});
            return fallback;
        }
    }
}
