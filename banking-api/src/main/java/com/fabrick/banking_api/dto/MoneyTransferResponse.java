package com.fabrick.banking_api.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class MoneyTransferResponse {
    private String status;
    private Error[] errors;
    private Object payload;

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Error {
        private String code;
        private String description;
    }
}
