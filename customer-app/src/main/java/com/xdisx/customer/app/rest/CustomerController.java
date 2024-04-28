package com.xdisx.customer.app.rest;

import com.xdisx.customer.api.CustomerApi;
import com.xdisx.customer.api.dto.request.CustomerCreateRequestDto;
import com.xdisx.customer.api.dto.response.CustomerResponseDto;
import com.xdisx.customer.app.service.CustomerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class CustomerController implements CustomerApi {
    private final CustomerService customerService;

    @Override
    public CustomerResponseDto createCustomer(CustomerCreateRequestDto customerCreateRequest) {
        log.info("Received Create customer request: {}", customerCreateRequest);
        return customerService.createCustomer(customerCreateRequest);
    }
}
