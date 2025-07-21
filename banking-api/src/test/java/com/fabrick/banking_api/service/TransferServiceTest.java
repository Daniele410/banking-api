package com.fabrick.banking_api.service;

import com.fabrick.banking_api.client.FabrickClient;
import com.fabrick.banking_api.config.FabrickProperties;
import com.fabrick.banking_api.dto.MoneyTransferRequest;
import com.fabrick.banking_api.dto.MoneyTransferResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import feign.FeignException;
import feign.Request;
import feign.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TransferServiceTest {

    private final Long accountId = 14537780L;
    private FabrickClient fabrickClient;
    private FabrickProperties fabrickProperties;
    private ObjectMapper objectMapper;
    private TransferService transferService;

    @BeforeEach
    void setUp() throws Exception {
        fabrickClient = mock(FabrickClient.class);
        fabrickProperties = mock(FabrickProperties.class);
        objectMapper = new ObjectMapper();

        when(fabrickProperties.getAuthSchema()).thenReturn("S2S");
        when(fabrickProperties.getKey()).thenReturn("FXOVVXXHVCPVPBZXIJOBGUGSKHDNFRRQJP");

        transferService = new TransferService(fabrickClient, fabrickProperties, objectMapper);

        // Inject accountId manually
        var accountIdField = TransferService.class.getDeclaredField("accountId");
        accountIdField.setAccessible(true);
        accountIdField.set(transferService, accountId);
    }

    @Test
    void testCreateTransfer_success() {
        // Given
        MoneyTransferRequest request = sampleRequest();

        MoneyTransferResponse expectedResponse = new MoneyTransferResponse();
        expectedResponse.setStatus("OK");
        expectedResponse.setPayload("{\"message\":\"Success\"}");

        when(fabrickClient.createMoneyTransfer("S2S", "FXOVVXXHVCPVPBZXIJOBGUGSKHDNFRRQJP", "Europe/Rome", accountId, request))
                .thenReturn(expectedResponse);

        // When
        MoneyTransferResponse actual = transferService.createTransfer(request);

        // Then
        assertNotNull(actual);
        assertEquals("OK", actual.getStatus());
        assertEquals(expectedResponse.getPayload(), actual.getPayload());
    }

    @Test
    void testCreateTransfer_badRequest() {
        // Given
        MoneyTransferRequest request = sampleRequest();

        String errorJson = """
                {
                  "status": "KO",
                  "errors": [{
                    "code": "API000",
                    "description": "Generic error"
                  }]
                }
                """;

        FeignException exception = FeignException.errorStatus(
                "createMoneyTransfer",
                Response.builder()
                        .status(400)
                        .reason("Bad Request")
                        .request(Request.create(
                                Request.HttpMethod.POST,
                                "/api/transfer",
                                Map.of(),
                                new byte[0],
                                StandardCharsets.UTF_8
                        ))
                        .body(errorJson, StandardCharsets.UTF_8)
                        .build()
        );

        when(fabrickClient.createMoneyTransfer(any(), any(), any(), any(), any()))
                .thenThrow(exception);

        // When
        MoneyTransferResponse result = transferService.createTransfer(request);

        // Then
        assertEquals("KO", result.getStatus());
        assertEquals("API000", result.getErrors()[0].getCode());
    }

    @Test
    void testCreateTransfer_parseErrorFallback() {
        // Given
        MoneyTransferRequest request = sampleRequest();

        String invalidJson = "invalid json";

        FeignException exception = FeignException.errorStatus(
                "createMoneyTransfer",
                Response.builder()
                        .status(400)
                        .reason("Bad Request")
                        .request(Request.create(
                                Request.HttpMethod.POST,
                                "/api/transfer",
                                Map.of(),
                                new byte[0],
                                StandardCharsets.UTF_8
                        ))
                        .body(invalidJson, StandardCharsets.UTF_8)
                        .build()
        );

        when(fabrickClient.createMoneyTransfer(any(), any(), any(), any(), any()))
                .thenThrow(exception);

        // When
        MoneyTransferResponse result = transferService.createTransfer(request);

        // Then
        assertEquals("KO", result.getStatus());
        assertEquals("REQ500", result.getErrors()[0].getCode());
    }

    @Test
    void testCreateTransfer_unexpectedException() {
        // Given
        MoneyTransferRequest request = new MoneyTransferRequest();
        request.setAmount(BigDecimal.valueOf(100.0));
        request.setCurrency("EUR");

        when(fabrickClient.createMoneyTransfer(any(), any(), any(), any(), any()))
                .thenThrow(new IllegalStateException("Unexpected error"));

        // When & Then
        Exception exception = assertThrows(IllegalStateException.class, () ->
                transferService.createTransfer(request)
        );

        assertEquals("Unexpected error", exception.getMessage());
    }

    private MoneyTransferRequest sampleRequest() {
        MoneyTransferRequest req = new MoneyTransferRequest();
        req.setAmount(BigDecimal.valueOf(100.0));
        req.setCurrency("EUR");
        return req;
    }
}
