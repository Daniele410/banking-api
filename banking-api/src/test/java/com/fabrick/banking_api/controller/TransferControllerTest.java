package com.fabrick.banking_api.controller;

import com.fabrick.banking_api.dto.MoneyTransferRequest;
import com.fabrick.banking_api.dto.MoneyTransferResponse;
import com.fabrick.banking_api.service.TransferService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TransferController.class)
class TransferControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private TransferService transferService;

    @Autowired
    private ObjectMapper objectMapper;

    private MoneyTransferRequest validRequest;

    @BeforeEach
    void setUp() {
        validRequest = new MoneyTransferRequest();
        validRequest.setAmount(BigDecimal.valueOf(100.0));
        validRequest.setCurrency("EUR");
        validRequest.setDescription("Test transfer");
        validRequest.setExecutionDate("2025-07-21");

        MoneyTransferRequest.Creditor creditor = new MoneyTransferRequest.Creditor();
        MoneyTransferRequest.Creditor.Account account = new MoneyTransferRequest.Creditor.Account();
        account.setAccountCode("IT23A0336844430152923804660");
        creditor.setName("Mario Rossi");
        creditor.setAccount(account);
        validRequest.setCreditor(creditor);
    }

    @Test
    void shouldReturnOkWhenTransferIsSuccessful() throws Exception {
        // Given
        MoneyTransferResponse successResponse = new MoneyTransferResponse();
        successResponse.setStatus("OK");
        successResponse.setPayload("{\"message\":\"Transfer succeeded\"}");

        Mockito.when(transferService.createTransfer(any())).thenReturn(successResponse);

        // When & Then
        mockMvc.perform(post("/transfer")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status", is("OK")))
                .andExpect(jsonPath("$.payload", containsString("Transfer succeeded")));
    }

    @Test
    void shouldReturnBadRequestWhenTransferFails() throws Exception {
        // Given
        MoneyTransferResponse.Error error = new MoneyTransferResponse.Error();
        error.setCode("API000");
        error.setDescription("Errore tecnico");

        MoneyTransferResponse errorResponse = new MoneyTransferResponse();
        errorResponse.setStatus("KO");
        errorResponse.setErrors(new MoneyTransferResponse.Error[]{error});

        Mockito.when(transferService.createTransfer(any())).thenReturn(errorResponse);

        // When & Then
        mockMvc.perform(post("/transfer")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status", is("KO")))
                .andExpect(jsonPath("$.errors[0].code", is("API000")))
                .andExpect(jsonPath("$.errors[0].description", is("Errore tecnico")));
    }
}