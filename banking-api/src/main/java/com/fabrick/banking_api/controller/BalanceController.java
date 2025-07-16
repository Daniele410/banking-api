package com.fabrick.banking_api.controller;

import com.fabrick.banking_api.dto.BalanceResponse;
import com.fabrick.banking_api.service.BalanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/balance")
@RequiredArgsConstructor
public class BalanceController {

    private final BalanceService balanceService;

    @GetMapping
    public ResponseEntity<BalanceResponse> getBalance() {
        BalanceResponse response = balanceService.getBalance();

        if (!"OK".equalsIgnoreCase(response.getStatus())) {
            return ResponseEntity.badRequest().body(response);
        }

        return ResponseEntity.ok(response);
    }
}
