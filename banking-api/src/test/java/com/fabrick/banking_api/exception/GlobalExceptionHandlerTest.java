package com.fabrick.banking_api.exception;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class GlobalExceptionHandlerTest {

    @Test
    void testHandleValidationErrors() {
        // Given
        BindException bindException = new BindException(new Object(), "testObject");
        bindException.addError(new FieldError("testObject", "field1", "Field1 is required"));
        bindException.addError(new FieldError("testObject", "field2", "Field2 must be a number"));

        MethodArgumentNotValidException ex = new MethodArgumentNotValidException(null, bindException.getBindingResult());
        GlobalExceptionHandler handler = new GlobalExceptionHandler();

        // When
        ResponseEntity<?> responseEntity = handler.handleValidationErrors(ex);

        // Then
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertTrue(responseEntity.getBody() instanceof Map);

        Map<?, ?> errors = (Map<?, ?>) responseEntity.getBody();
        assertEquals("Field1 is required", errors.get("field1"));
        assertEquals("Field2 must be a number", errors.get("field2"));
    }
}