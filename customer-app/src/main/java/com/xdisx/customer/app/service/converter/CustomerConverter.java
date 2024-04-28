package com.xdisx.customer.app.service.converter;

import com.xdisx.customer.api.dto.CustomerTypeDto;
import com.xdisx.customer.api.dto.request.CustomerCreateRequestDto;
import com.xdisx.customer.api.dto.response.CustomerResponseDto;
import com.xdisx.customer.app.repository.db.entity.CustomerEntity;
import com.xdisx.customer.app.repository.db.entity.CustomerType;
import lombok.experimental.UtilityClass;

@UtilityClass
public class CustomerConverter {
    public CustomerEntity fromCreateRequest(CustomerCreateRequestDto createRequestDto) {
    CustomerEntity customerEntity = new CustomerEntity();
        customerEntity.setCustomerType(CustomerType.valueOf(createRequestDto.getCustomerType().toString()));

        return customerEntity;
    }

    public CustomerResponseDto toCustomerResponse(CustomerEntity customer) {
        return CustomerResponseDto.builder().ID(customer.getId()).customerType(CustomerTypeDto.valueOf(customer.getCustomerType().toString())).build();
    }
}
