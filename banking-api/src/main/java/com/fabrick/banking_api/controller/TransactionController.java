package com.fabrick.banking_api.controller;

import com.fabrick.banking_api.dto.TransactionResponse;
import com.fabrick.banking_api.service.TransactionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/transactions")
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionService transactionService;

    @GetMapping
    public ResponseEntity<TransactionResponse> getTransactions(@RequestParam("from") String fromDate, @RequestParam("to") String toDate) {
        log.info("Fetching transactions from {} to {}", fromDate, toDate);
        TransactionResponse response = transactionService.getTransactions(fromDate, toDate);
        if ("OK".equalsIgnoreCase(response.getStatus())) {
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.badRequest().body(response);
    }
}
