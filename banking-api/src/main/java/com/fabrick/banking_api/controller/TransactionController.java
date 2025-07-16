package com.fabrick.banking_api.controller;

import com.fabrick.banking_api.dto.TransactionResponse;
import com.fabrick.banking_api.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/transactions")
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionService transactionService;

    @GetMapping
    public ResponseEntity<TransactionResponse> getTransactions(@RequestParam("from") String fromDate, @RequestParam("to") String toDate) {
        return new ResponseEntity<>(transactionService.getTransactions(fromDate, toDate), HttpStatus.OK);
    }
}
