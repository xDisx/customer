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
import com.xdisx.customer.app.repository.db.filtering.CustomerSpecificationBuilder;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
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
  @Mock private ContractRepository contractRepository;
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
  void testBuildEmailLikeSpecification() {
    Specification<CustomerEntity> spec =
        CustomerSpecificationBuilder.buildEmailLikeSpecification("example@example.com");

    CriteriaBuilder cb = mock(CriteriaBuilder.class);
    Root<CustomerEntity> root = mock(Root.class);
    CriteriaQuery<?> query = mock(CriteriaQuery.class);
    Predicate predicate = mock(Predicate.class);

    // Specifying the exact method by casting matchers
    when(cb.like(any(Expression.class), anyString())).thenReturn(predicate);

    // Mocking the path to get 'email'
    Path<String> emailPath = mock(Path.class);
    when(root.<String>get("email")).thenReturn(emailPath);
    when(cb.lower(emailPath)).thenReturn(emailPath);

    Predicate result = spec.toPredicate(root, query, cb);

    verify(cb).like(emailPath, "%example@example.com%");
    assertNotNull(result);
  }

  @Test
  void testBuildNameLikeSpecification() {
    Specification<CustomerEntity> spec =
        CustomerSpecificationBuilder.buildNameLikeSpecification("John");

    CriteriaBuilder cb = mock(CriteriaBuilder.class);
    Root<CustomerEntity> root = mock(Root.class);
    CriteriaQuery<?> query = mock(CriteriaQuery.class);
    Predicate predicate = mock(Predicate.class);

    // Use lenient to avoid strict stubbing issues
    lenient().when(cb.like(any(Expression.class), anyString())).thenReturn(predicate);

    Predicate result = spec.toPredicate(root, query, cb);

    // Capturing arguments for further assertions if needed
    ArgumentCaptor<Expression> expressionCaptor = ArgumentCaptor.forClass(Expression.class);
    ArgumentCaptor<String> stringCaptor = ArgumentCaptor.forClass(String.class);
    verify(cb, times(2)).like(expressionCaptor.capture(), stringCaptor.capture());
    assertTrue(stringCaptor.getValue().contains("john"));

    verify(cb, times(2)).lower(root.get("firstName"));
    verify(cb, times(2)).lower(root.get("lastName"));
  }
}
