package com.xdisx.customer.app.service.converter;

import com.xdisx.customer.api.dto.CustomerTypeDto;
import com.xdisx.customer.api.dto.request.CustomerCreateRequestDto;
import com.xdisx.customer.api.dto.request.CustomerPageRequestDto;
import com.xdisx.customer.api.dto.response.CustomerResponseDto;
import com.xdisx.customer.app.repository.db.dto.CustomerPageDto;
import com.xdisx.customer.app.repository.db.entity.CustomerEntity;
import com.xdisx.customer.app.repository.db.entity.CustomerType;
import java.util.List;
import lombok.experimental.UtilityClass;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

@UtilityClass
public class CustomerConverter {
  public CustomerEntity fromCreateRequest(CustomerCreateRequestDto createRequestDto) {
    CustomerEntity customerEntity = new CustomerEntity();
    customerEntity.setCustomerType(
        CustomerType.valueOf(createRequestDto.getCustomerType().toString()));

    return customerEntity;
  }

  public CustomerResponseDto toCustomerResponse(CustomerEntity customer) {
    return CustomerResponseDto.builder()
        .ID(customer.getId())
        .customerType(CustomerTypeDto.valueOf(customer.getCustomerType().toString()))
        .build();
  }

  public static PageRequest toPageRequest(CustomerPageRequestDto customerPageRequestDto) {
    return PageRequest.of(
        customerPageRequestDto.getPageNumber(),
        customerPageRequestDto.getPageSize(),
        Sort.by(
            Sort.Direction.valueOf(customerPageRequestDto.getOrderBy().toString()),
            customerPageRequestDto.getSortBy()));
  }

  public static List<CustomerResponseDto> toListCustomerResponse(Page<CustomerPageDto> result) {
    return result.map(CustomerConverter::toCustomerResponse).stream().toList();
  }

  private CustomerResponseDto toCustomerResponse(CustomerPageDto pageDto) {
    return CustomerResponseDto.builder()
        .ID(pageDto.getId())
        .customerType(CustomerTypeDto.valueOf(pageDto.getCustomerType().toString()))
        .build();
  }
}
