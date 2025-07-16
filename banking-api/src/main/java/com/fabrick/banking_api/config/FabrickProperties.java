package com.fabrick.banking_api.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "fabrick.api")
public class FabrickProperties {
    private String baseUrl;
    private String key;
    private String authSchema;
}
