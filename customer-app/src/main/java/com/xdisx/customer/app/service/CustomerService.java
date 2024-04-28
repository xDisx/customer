package com.xdisx.customer.app.service;

import com.xdisx.customer.api.dto.request.CustomerCreateRequestDto;
import com.xdisx.customer.api.dto.response.CustomerResponseDto;

public interface CustomerService {
    CustomerResponseDto createCustomer(CustomerCreateRequestDto customerCreateRequestDto);
}
