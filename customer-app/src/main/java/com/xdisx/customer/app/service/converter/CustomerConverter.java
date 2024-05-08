package com.xdisx.customer.app.service.converter;

import com.xdisx.customer.api.dto.request.CustomerCreateRequestDto;
import com.xdisx.customer.api.dto.request.CustomerPageRequestDto;
import com.xdisx.customer.api.dto.response.CustomerResponseDto;
import com.xdisx.customer.app.repository.db.dto.CustomerPageDto;
import com.xdisx.customer.app.repository.db.entity.CustomerEntity;
import java.util.List;
import lombok.experimental.UtilityClass;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

@UtilityClass
public class CustomerConverter {
  public CustomerEntity fromCreateRequest(CustomerCreateRequestDto createRequestDto) {
    CustomerEntity customerEntity = new CustomerEntity();
    customerEntity.setFirstName(createRequestDto.getFirstName());
    customerEntity.setLastName(createRequestDto.getLastName());
    customerEntity.setEmail(createRequestDto.getEmail());
    customerEntity.setPhoneNumber(createRequestDto.getPhoneNumber());
    customerEntity.setAddress(createRequestDto.getAddress());

    return customerEntity;
  }

  public CustomerResponseDto toCustomerResponse(CustomerEntity customer) {
    return CustomerResponseDto.builder()
        .ID(customer.getId())
        .firstName(customer.getFirstName())
        .lastName(customer.getLastName())
        .email(customer.getEmail())
        .phoneNumber(customer.getPhoneNumber())
        .address(customer.getAddress())
        .created(customer.getCreated())
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
        .firstName(pageDto.getFirstName())
        .lastName(pageDto.getLastName())
        .email(pageDto.getEmail())
        .phoneNumber(pageDto.getPhoneNumber())
        .address(pageDto.getAddress())
        .created(pageDto.getCreated())
        .build();
  }
}
