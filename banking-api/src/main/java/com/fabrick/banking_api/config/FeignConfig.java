package com.fabrick.banking_api.config;

import com.fabrick.banking_api.exception.FabrickErrorDecoder;
import feign.codec.ErrorDecoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignConfig {

    @Bean
    public ErrorDecoder fabrickErrorDecoder() {
        return new FabrickErrorDecoder();
    }

}
