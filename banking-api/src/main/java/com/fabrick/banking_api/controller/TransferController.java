package com.fabrick.banking_api.controller;

import com.fabrick.banking_api.dto.MoneyTransferRequest;
import com.fabrick.banking_api.dto.MoneyTransferResponse;
import com.fabrick.banking_api.service.TransferService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/transfer")
@RequiredArgsConstructor
@Transactional
public class TransferController {

    private final TransferService transferService;

    @PostMapping
    public ResponseEntity<Map<String, Object>> createTransfer(@Valid @RequestBody MoneyTransferRequest request) {
        log.info("Received POST /transfer request: {}", request);

        MoneyTransferResponse response = transferService.createTransfer(request);

        if ("KO".equalsIgnoreCase(response.getStatus())) {
            log.warn("Transfer failed with errors: {}", (Object) response.getErrors());
            return ResponseEntity
                    .badRequest()
                    .body(Map.of(
                            "status", response.getStatus(),
                            "errors", response.getErrors()
                    ));
        }

        log.info("Transfer completed successfully with status: {}", response.getStatus());

        return ResponseEntity.ok(Map.of(
                "status", response.getStatus(),
                "payload", response.getPayload()
        ));
    }
}
