package com.fabrick.banking_api.exception;

import com.fabrick.banking_api.dto.MoneyTransferResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import feign.Response;
import feign.codec.ErrorDecoder;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;

public class FabrickErrorDecoder implements ErrorDecoder {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public Exception decode(String methodKey, Response response) {
        try {
            MoneyTransferResponse errorBody = objectMapper.readValue(response.body().asInputStream(), MoneyTransferResponse.class);
            return new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    errorBody.getErrors()[0].getDescription()
            );
        } catch (IOException e) {
            return new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error during the decoding of the Fabrick error", e);
        }
    }
}
