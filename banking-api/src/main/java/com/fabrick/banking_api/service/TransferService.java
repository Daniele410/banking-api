package com.fabrick.banking_api.service;

import com.fabrick.banking_api.client.FabrickClient;
import com.fabrick.banking_api.config.FabrickProperties;
import com.fabrick.banking_api.dto.MoneyTransferRequest;
import com.fabrick.banking_api.dto.MoneyTransferResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class TransferService {

    private final FabrickClient fabrickClient;
    private final FabrickProperties fabrickProperties;

    @Value("${fabrick.account-id}")
    private Long accountId;

    private final ObjectMapper objectMapper;

    public MoneyTransferResponse createTransfer(MoneyTransferRequest request) {
        log.info("Initiating money transfer for accountId={} with amount={} {}",
                accountId, request.getAmount(), request.getCurrency());
        try {
            MoneyTransferResponse response = fabrickClient.createMoneyTransfer(
                    fabrickProperties.getAuthSchema(),
                    fabrickProperties.getKey(),
                    "Europe/Rome",
                    accountId,
                    request
            );
            log.info("Transfer response received: status={}, payload={}",
                    response.getStatus(), response.getPayload());
            return response;
        } catch (FeignException.BadRequest ex) {
            log.warn("BadRequest received from Fabrick: {}", ex.contentUTF8());
            return parseErrorResponse(ex);
        } catch (Exception e) {
            log.error("Unexpected error during transfer: {}", e.getMessage(), e);
            throw e;
        }
    }

    private MoneyTransferResponse parseErrorResponse(FeignException.BadRequest ex) {
        try {
            return objectMapper.readValue(ex.contentUTF8(), MoneyTransferResponse.class);
        } catch (Exception e) {
            log.error("Error parsing Fabrick error response: {}", e.getMessage(), e);
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
