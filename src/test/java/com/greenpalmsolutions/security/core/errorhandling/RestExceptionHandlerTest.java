package com.greenpalmsolutions.security.core.errorhandling;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;

class RestExceptionHandlerTest {

    private RestExceptionHandler restExceptionHandler;

    @BeforeEach
    void init() {
        restExceptionHandler = new RestExceptionHandler();
    }

    @Test
    void handlesInvalidRequest() {
        ResponseEntity<?> theResponse = restExceptionHandler.handleInvalidRequest(
                new InvalidRequestException("Bad input"));

        assertThat(theResponse.getStatusCode().value()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(((ApiError) Objects.requireNonNull(theResponse.getBody())).getMessage())
                .isEqualTo("Bad input");
    }

    @Test
    void handlesException() {
        ResponseEntity<?> theResponse = restExceptionHandler.handleException(
                new RuntimeException("Error"));

        assertThat(theResponse.getStatusCode().value()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR.value());
        assertThat(((ApiError) Objects.requireNonNull(theResponse.getBody())).getMessage())
                .isEqualTo("An error has occurred");
    }
}