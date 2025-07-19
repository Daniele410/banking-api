package com.fabrick.banking_api.controller;

import com.fabrick.banking_api.dto.MoneyTransferRequest;
import com.fabrick.banking_api.dto.MoneyTransferResponse;
import com.fabrick.banking_api.service.TransferService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/transfer")
@RequiredArgsConstructor
public class TransferController {

    private final TransferService transferService;

    @PostMapping
    public ResponseEntity<Map<String, Object>> createTransfer(@Valid @RequestBody MoneyTransferRequest request) {
        MoneyTransferResponse response = transferService.createTransfer(request);

        if ("KO".equalsIgnoreCase(response.getStatus())) {
            return ResponseEntity
                    .badRequest()
                    .body(Map.of(
                            "status", response.getStatus(),
                            "errors", response.getErrors()
                    ));
        }

        return ResponseEntity.ok(Map.of(
                "status", response.getStatus(),
                "payload", response.getPayload()
        ));
    }

}
