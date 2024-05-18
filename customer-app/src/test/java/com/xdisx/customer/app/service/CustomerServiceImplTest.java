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
import com.xdisx.customer.api.exception.CustomerNotFoundException;
import com.xdisx.customer.app.mock.CustomerMock;
import com.xdisx.customer.app.mock.CustomerPageDtoMock;
import com.xdisx.customer.app.repository.db.CustomerRepository;
import com.xdisx.customer.app.repository.db.dto.CustomerPageDto;
import com.xdisx.customer.app.repository.db.entity.CustomerEntity;
import java.math.BigInteger;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeAll;
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
  private static CustomerEntity matchingCustomer;
  private static CustomerEntity nonMatchingCustomer;
  @Mock private CustomerRepository customerRepository;
  @InjectMocks private CustomerServiceImpl classUnderTest;

  @BeforeAll
  public static void setUp() {
    matchingCustomer = new CustomerEntity();
    matchingCustomer.setFirstName("John");
    matchingCustomer.setLastName("Doe");
    matchingCustomer.setEmail("johndoe@example.com");
    matchingCustomer.setPhoneNumber("1234567890");

    nonMatchingCustomer = new CustomerEntity();
    nonMatchingCustomer.setFirstName("Jane");
    nonMatchingCustomer.setLastName("Smith");
    nonMatchingCustomer.setEmail("janesmith@example.com");
    nonMatchingCustomer.setPhoneNumber("0987654321");
  }

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
  void testFindCustomersByAllCriteria() {
    Pageable pageable = PageRequest.of(0, 10);
    List<CustomerPageDto> customerPageDtos = List.of(new CustomerPageDtoMock());
    Page<CustomerPageDto> contractPage =
        new PageImpl<>(customerPageDtos, pageable, customerPageDtos.size());

    when(customerRepository.findBy(ArgumentMatchers.<Specification<CustomerEntity>>any(), any()))
        .thenReturn(contractPage);

    CustomerPageResponseDto result =
        classUnderTest.findCustomers(
            CustomerPageRequestDto.builder()
                .customerName(CustomerMock.FIRST_NAME)
                .email(CustomerMock.EMAIL)
                .phoneNumber(CustomerMock.PHONE_NUMBER)
                .build());

    assertNotNull(result);
    assertEquals(customerPageDtos.size(), result.getCustomers().size());

    verify(customerRepository).findBy(ArgumentMatchers.<Specification<CustomerEntity>>any(), any());
  }

  @Test
  void testGetCustomer_Success() {
    CustomerEntity mockCustomer = CustomerMock.getCustomerEntity();

    when(customerRepository.findById(mockCustomer.getId())).thenReturn(Optional.of(mockCustomer));
    CustomerResponseDto expectedResponse = CustomerMock.getCustomerResponse();

    CustomerResponseDto actualResponse = classUnderTest.getCustomer(mockCustomer.getId());

    assertEquals(expectedResponse, actualResponse);
    verify(customerRepository).findById(mockCustomer.getId());
  }

  @Test
  void testGetCustomer_NotFound() {
    BigInteger id = BigInteger.ONE;
    when(customerRepository.findById(id)).thenReturn(Optional.empty());

    assertThrows(
        CustomerNotFoundException.class,
        () -> {
          classUnderTest.getCustomer(id);
        });

    verify(customerRepository).findById(id);
  }
}
