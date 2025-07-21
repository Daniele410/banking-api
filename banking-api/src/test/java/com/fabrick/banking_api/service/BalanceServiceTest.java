package com.fabrick.banking_api.service;

import com.fabrick.banking_api.client.FabrickClient;
import com.fabrick.banking_api.config.FabrickProperties;
import com.fabrick.banking_api.dto.BalanceResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BalanceServiceTest {

    private final Long accountId = 14537780L;
    private FabrickClient fabrickClient;
    private FabrickProperties fabrickProperties;
    private BalanceService balanceService;

    @BeforeEach
    void setUp() throws Exception {
        // Given
        fabrickClient = mock(FabrickClient.class);
        fabrickProperties = mock(FabrickProperties.class);

        when(fabrickProperties.getAuthSchema()).thenReturn("S2S");
        when(fabrickProperties.getKey()).thenReturn("FXOVVXXHVCPVPBZXIJOBGUGSKHDNFRRQJP");

        balanceService = new BalanceService(fabrickClient, fabrickProperties);

        // Inject accountId manually (since @Value is not processed in tests)
        var field = BalanceService.class.getDeclaredField("accountId");
        field.setAccessible(true);
        field.set(balanceService, accountId);
    }

    @Test
    void testGetBalance_success() {
        // Given
        BalanceResponse expected = new BalanceResponse();
        expected.setStatus("OK");

        when(fabrickClient.getBalance("S2S", "FXOVVXXHVCPVPBZXIJOBGUGSKHDNFRRQJP", accountId))
                .thenReturn(expected);

        // When
        BalanceResponse result = balanceService.getBalance();

        // Then
        assertNotNull(result);
        assertEquals("OK", result.getStatus());
        verify(fabrickClient, times(1)).getBalance("S2S", "FXOVVXXHVCPVPBZXIJOBGUGSKHDNFRRQJP", accountId);
    }

    @Test
    void testGetBalance_failure() {
        // Given
        when(fabrickClient.getBalance(any(), any(), anyLong()))
                .thenThrow(new RuntimeException("Connection error"));

        // When + Then
        Exception ex = assertThrows(RuntimeException.class, () -> balanceService.getBalance());
        assertEquals("Connection error", ex.getMessage());
        verify(fabrickClient, times(1)).getBalance(any(), any(), anyLong());
    }
}