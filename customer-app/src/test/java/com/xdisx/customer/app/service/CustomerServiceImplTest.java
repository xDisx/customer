package com.xdisx.customer.app.service;

import static com.xdisx.customer.app.service.CustomerServiceImpl.CUSTOMER_SAVE_ERROR;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.*;

import com.xdisx.customer.api.dto.request.CustomerCreateRequestDto;
import com.xdisx.customer.api.dto.request.CustomerPageRequestDto;
import com.xdisx.customer.api.dto.response.CustomerPageResponseDto;
import com.xdisx.customer.api.dto.response.CustomerResponseDto;
import com.xdisx.customer.api.exception.CustomerCreateException;
import com.xdisx.customer.app.mock.CustomerMock;
import com.xdisx.customer.app.mock.CustomerPageDtoMock;
import com.xdisx.customer.app.repository.contract.ContractRepository;
import com.xdisx.customer.app.repository.db.CustomerRepository;
import com.xdisx.customer.app.repository.db.dto.CustomerPageDto;
import com.xdisx.customer.app.repository.db.entity.CustomerEntity;
import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

@ExtendWith(MockitoExtension.class)
class CustomerServiceImplTest {
  @Mock private CustomerRepository customerRepository;

  @Mock private ContractRepository contractRepository;

  @InjectMocks private CustomerServiceImpl classUnderTest;

  @Test
  void createCustomer() {
    CustomerCreateRequestDto requestDto = CustomerMock.getCreateCustomerRequest();
    CustomerEntity customer = CustomerMock.getCustomerEntity(requestDto);
    CustomerEntity customer2 = CustomerMock.getCustomerEntity(requestDto);
    assertEquals(customer, customer2);

    when(customerRepository.saveAndFlush(
            argThat(arg -> requestDto.getEmail().equals(arg.getEmail()))))
        .thenReturn(customer);
    var savedCustomer = classUnderTest.createCustomer(requestDto);

    verify(customerRepository)
        .saveAndFlush(
            argThat(
                arg -> {
                  assertEquals(requestDto.getEmail(), arg.getEmail());
                  return true;
                }));

    assertNotNull(savedCustomer);
    assertCustomerResponseWithRequest(requestDto, savedCustomer);
  }

  private void assertCustomerResponseWithRequest(
      CustomerCreateRequestDto requestDto, CustomerResponseDto responseDto) {
    assertEquals(requestDto.getFirstName(), responseDto.getFirstName());
    assertEquals(requestDto.getLastName(), responseDto.getLastName());
    assertEquals(requestDto.getEmail(), responseDto.getEmail());
    assertEquals(requestDto.getPhoneNumber(), responseDto.getPhoneNumber());
    assertEquals(requestDto.getAddress(), responseDto.getAddress());
  }

  @Test
  void createContract_throwsExceptionWhenDataIntegrityIsViolated() {
    CustomerCreateRequestDto requestDto = CustomerMock.getCreateCustomerRequest();

    doThrow(new DataIntegrityViolationException("Database error"))
        .when(customerRepository)
        .saveAndFlush(any(CustomerEntity.class));

    CustomerCreateException thrown =
        assertThrows(
            CustomerCreateException.class,
            () -> {
              classUnderTest.createCustomer(requestDto);
            },
            "Expected createCustomer to throw, but it didn't");

    assertEquals(CUSTOMER_SAVE_ERROR, thrown.getMessage(), "Exception message does not match");
  }

  @Test
  void findCustomersReturnsCorrectData() {
    Pageable pageable = PageRequest.of(0, 10);
    List<CustomerPageDto> customerEntityList = List.of(new CustomerPageDtoMock());
    Page<CustomerPageDto> contractPage =
        new PageImpl<>(customerEntityList, pageable, customerEntityList.size());

    when(customerRepository.findBy(ArgumentMatchers.<Specification<CustomerEntity>>any(), any()))
        .thenReturn(contractPage);

    CustomerPageResponseDto result =
        classUnderTest.findCustomers(CustomerPageRequestDto.builder().build());

    assertNotNull(result);
    assertEquals(customerEntityList.size(), result.getCustomers().size());

    verify(customerRepository).findBy(ArgumentMatchers.<Specification<CustomerEntity>>any(), any());
  }

  @Test
  void testFindCustomersByCreatedOn() {
    Pageable pageable = PageRequest.of(0, 10);
    List<CustomerPageDto> customerPageDtos = List.of(new CustomerPageDtoMock());
    Page<CustomerPageDto> contractPage =
        new PageImpl<>(customerPageDtos, pageable, customerPageDtos.size());

    when(customerRepository.findBy(ArgumentMatchers.<Specification<CustomerEntity>>any(), any()))
        .thenReturn(contractPage);

    CustomerPageResponseDto result =
        classUnderTest.findCustomers(
            CustomerPageRequestDto.builder().createdOn(LocalDate.now()).build());

    assertNotNull(result);
    assertEquals(customerPageDtos.size(), result.getCustomers().size());

    verify(customerRepository).findBy(ArgumentMatchers.<Specification<CustomerEntity>>any(), any());
  }
}
