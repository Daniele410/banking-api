package com.fabrick.banking_api.controller;

import com.fabrick.banking_api.dto.TransactionResponse;
import com.fabrick.banking_api.service.TransactionService;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class TransactionControllerTest {

    @Test
    void shouldReturnOkResponse() {
        // Given
        TransactionService mockService = mock(TransactionService.class);
        TransactionResponse response = new TransactionResponse();
        response.setStatus("OK");

        when(mockService.getTransactions("2024-01-01", "2024-12-31")).thenReturn(response);
        TransactionController controller = new TransactionController(mockService);

        // When
        ResponseEntity<TransactionResponse> result = controller.getTransactions("2024-01-01", "2024-12-31");

        // Then
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals("OK", result.getBody().getStatus());
    }

    @Test
    void shouldReturnBadRequestOnKoStatus() {
        // Given
        TransactionService mockService = mock(TransactionService.class);
        TransactionResponse response = new TransactionResponse();
        response.setStatus("KO");

        when(mockService.getTransactions("2024-01-01", "2024-12-31")).thenReturn(response);
        TransactionController controller = new TransactionController(mockService);

        // When
        ResponseEntity<TransactionResponse> result = controller.getTransactions("2024-01-01", "2024-12-31");

        // Then
        assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode());
        assertEquals("KO", result.getBody().getStatus());
    }
}