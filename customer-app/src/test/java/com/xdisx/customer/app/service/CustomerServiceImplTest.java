package com.xdisx.customer.app.service;

import com.xdisx.customer.api.dto.CustomerTypeDto;
import com.xdisx.customer.api.dto.request.CustomerCreateRequestDto;
import com.xdisx.customer.api.dto.response.CustomerResponseDto;
import com.xdisx.customer.app.mock.CustomerMock;
import com.xdisx.customer.app.repository.db.CustomerRepository;
import com.xdisx.customer.app.repository.db.entity.CustomerEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CustomerServiceImplTest {
  @Mock
  private CustomerRepository customerRepository;

  @InjectMocks
  private CustomerServiceImpl classUnderTest;

  @Test
  void createCustomer() {
    CustomerCreateRequestDto requestDto = CustomerMock.getCreateCustomerRequest();
    CustomerEntity customer = CustomerMock.getCustomerEntity(requestDto);

    when(customerRepository.saveAndFlush(argThat(arg ->requestDto.getCustomerType().equals(CustomerTypeDto.valueOf(arg.getCustomerType().toString()))))).thenReturn(customer);
    var savedCustomer = classUnderTest.createCustomer(requestDto);

    verify(customerRepository).saveAndFlush(argThat(arg -> {
      assertEquals(requestDto.getCustomerType(), CustomerTypeDto.valueOf(arg.getCustomerType().toString()));
      return true;
    }));

    assertNotNull(savedCustomer);
    assertCustomerResponseWithRequest(requestDto, savedCustomer);
  }

  private void assertCustomerResponseWithRequest(CustomerCreateRequestDto requestDto, CustomerResponseDto responseDto) {
    assertEquals(requestDto.getCustomerType(), responseDto.getCustomerType());
  }
}
