package com.fabrick.banking_api.service;

import com.fabrick.banking_api.client.FabrickClient;
import com.fabrick.banking_api.config.FabrickProperties;
import com.fabrick.banking_api.dto.TransactionResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

class TransactionServiceTest {

    private final Long accountId = 14537780L;
    private FabrickClient fabrickClient;
    private FabrickProperties fabrickProperties;
    private TransactionService transactionService;

    @BeforeEach
    void setUp() {
        fabrickClient = mock(FabrickClient.class);
        fabrickProperties = mock(FabrickProperties.class);

        when(fabrickProperties.getAuthSchema()).thenReturn("S2S");
        when(fabrickProperties.getKey()).thenReturn("FXOVVXXHVCPVPBZXIJOBGUGSKHDNFRRQJP");

        transactionService = new TransactionService(fabrickClient, fabrickProperties);
        // Inject accountId manually since @Value is not processed in unit tests
        var accountIdField = TransactionService.class.getDeclaredFields()[2];
        accountIdField.setAccessible(true);
        try {
            accountIdField.set(transactionService, accountId);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void testGetTransactions_success() {
        String from = "2019-01-01";
        String to = "2019-12-01";
        TransactionResponse mockResponse = new TransactionResponse();
        mockResponse.setStatus("OK");

        when(fabrickClient.getTransactions("S2S", "FXOVVXXHVCPVPBZXIJOBGUGSKHDNFRRQJP", accountId, from, to))
                .thenReturn(mockResponse);

        TransactionResponse result = transactionService.getTransactions(from, to);

        assertNotNull(result);
        assertEquals("OK", result.getStatus());
        verify(fabrickClient, times(1)).getTransactions("S2S", "FXOVVXXHVCPVPBZXIJOBGUGSKHDNFRRQJP", accountId, from, to);
    }

}