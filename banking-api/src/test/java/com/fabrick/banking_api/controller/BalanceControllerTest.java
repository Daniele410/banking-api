package com.fabrick.banking_api.controller;

import com.fabrick.banking_api.dto.BalanceResponse;
import com.fabrick.banking_api.service.BalanceService;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class BalanceControllerTest {

    @Test
    void shouldReturnOkResponseWhenServiceReturnsOk() {
        // Given
        BalanceService mockService = mock(BalanceService.class);
        BalanceResponse mockResponse = new BalanceResponse();
        mockResponse.setStatus("OK");
        when(mockService.getBalance()).thenReturn(mockResponse);

        BalanceController controller = new BalanceController(mockService);

        // When
        ResponseEntity<BalanceResponse> result = controller.getBalance();

        // Then
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals("OK", result.getBody().getStatus());
    }

    @Test
    void shouldReturnBadRequestWhenServiceReturnsKo() {
        // Given
        BalanceService mockService = mock(BalanceService.class);
        BalanceResponse mockResponse = new BalanceResponse();
        mockResponse.setStatus("KO");
        when(mockService.getBalance()).thenReturn(mockResponse);

        BalanceController controller = new BalanceController(mockService);

        // When
        ResponseEntity<BalanceResponse> result = controller.getBalance();

        // Then
        assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode());
        assertNotNull(result.getBody());
        assertEquals("KO", result.getBody().getStatus());
    }

}