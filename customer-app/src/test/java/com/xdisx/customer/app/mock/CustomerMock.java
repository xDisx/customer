package com.xdisx.customer.app.mock;

import com.xdisx.customer.api.dto.CustomerTypeDto;
import com.xdisx.customer.api.dto.request.CustomerCreateRequestDto;
import com.xdisx.customer.api.dto.response.CustomerPageResponseDto;
import com.xdisx.customer.api.dto.response.CustomerResponseDto;
import com.xdisx.customer.app.repository.db.entity.CustomerEntity;
import com.xdisx.customer.app.repository.db.entity.CustomerType;
import java.math.BigInteger;
import java.util.List;
import lombok.experimental.UtilityClass;

@UtilityClass
public class CustomerMock {
    private static final CustomerTypeDto CUSTOMER_TYPE = CustomerTypeDto.PRIVATE;
    private static final BigInteger CUSTOMER_ID = BigInteger.ONE;

    public static CustomerCreateRequestDto getCreateCustomerRequest() {
        return CustomerCreateRequestDto.builder().customerType(CUSTOMER_TYPE).build();
    }

    public static CustomerResponseDto getCustomerResponse() {
        return CustomerResponseDto.builder().customerType(CUSTOMER_TYPE).ID(CUSTOMER_ID).build();
    }

    public static CustomerEntity getCustomerEntity(CustomerCreateRequestDto requestDto) {
        CustomerEntity customerEntity = new CustomerEntity();
        customerEntity.setCustomerType(CustomerType.valueOf(requestDto.getCustomerType().toString()));
        customerEntity.setId(CUSTOMER_ID);

        return customerEntity;
    }

    public static CustomerPageResponseDto getCustomerPageResponse() {
        CustomerPageResponseDto customerPageResponseDto = new CustomerPageResponseDto();
        customerPageResponseDto.setTotalPages(1);
        customerPageResponseDto.setTotalElements(1);
        customerPageResponseDto.setCustomers(List.of(getCustomerResponse()));

        return customerPageResponseDto;
    }
}
