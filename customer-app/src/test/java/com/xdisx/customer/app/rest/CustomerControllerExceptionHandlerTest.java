package com.xdisx.customer.app.rest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

import com.xdisx.customer.api.exception.CustomerCreateException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.WebRequest;

@ExtendWith(MockitoExtension.class)
class CustomerControllerExceptionHandlerTest {
    @InjectMocks
    private CustomerControllerExceptionHandler classUnderTest;

    @Test
    void handleCustomerCreateException() {
        ResponseEntity<Object> responseEntity = classUnderTest.handleCustomerCreateException(mock(WebRequest.class), mock(CustomerCreateException.class));
        assertEquals(BAD_REQUEST, responseEntity.getStatusCode());
    }
}
