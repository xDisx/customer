package com.xdisx.customer.app.mock;

import com.xdisx.customer.api.dto.request.CustomerCreateRequestDto;
import com.xdisx.customer.api.dto.response.CustomerPageResponseDto;
import com.xdisx.customer.api.dto.response.CustomerResponseDto;
import com.xdisx.customer.app.repository.db.entity.CustomerEntity;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import lombok.experimental.UtilityClass;

@UtilityClass
public class CustomerMock {
  public static final BigInteger CUSTOMER_ID = BigInteger.ONE;
  public static final String FIRST_NAME = "Ion";
  public static final String LAST_NAME = "Pop";
  public static final String PHONE_NUMBER = "+49134243";
  public static final String EMAIL = "ion.pop@yahoo.com";
  public static final String ADDRESS = "Fagului 21";
  public static final String CREATED = "06.10.2020 18:18:18";
  private static final DateTimeFormatter formatter =
      DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");

  public static CustomerCreateRequestDto getCreateCustomerRequest() {
    return CustomerCreateRequestDto.builder()
        .address(ADDRESS)
        .email(EMAIL)
        .firstName(FIRST_NAME)
        .lastName(LAST_NAME)
        .phoneNumber(PHONE_NUMBER)
        .build();
  }

  public static CustomerResponseDto getCustomerResponse() {
    return CustomerResponseDto.builder()
        .address(ADDRESS)
        .email(EMAIL)
        .firstName(FIRST_NAME)
        .lastName(LAST_NAME)
        .phoneNumber(PHONE_NUMBER)
        .ID(CUSTOMER_ID)
        .created(LocalDateTime.parse(CREATED, formatter))
        .build();
  }

  public static CustomerEntity getCustomerEntity(CustomerCreateRequestDto requestDto) {
    CustomerEntity customerEntity = new CustomerEntity();

    customerEntity.setId(CUSTOMER_ID);
    customerEntity.setFirstName(requestDto.getFirstName());
    customerEntity.setLastName(requestDto.getLastName());
    customerEntity.setPhoneNumber(requestDto.getPhoneNumber());
    customerEntity.setEmail(requestDto.getEmail());
    customerEntity.setAddress(requestDto.getAddress());

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
