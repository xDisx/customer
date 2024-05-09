package com.xdisx.customer.app.service;

import com.xdisx.customer.api.dto.request.CustomerCreateRequestDto;
import com.xdisx.customer.api.dto.request.CustomerPageRequestDto;
import com.xdisx.customer.api.dto.response.CustomerPageResponseDto;
import com.xdisx.customer.api.dto.response.CustomerResponseDto;
import java.math.BigInteger;

public interface CustomerService {
  CustomerResponseDto createCustomer(CustomerCreateRequestDto customerCreateRequestDto);

  CustomerPageResponseDto findCustomers(CustomerPageRequestDto customerPageRequestDto);

    CustomerResponseDto getCustomer(BigInteger id);
}
