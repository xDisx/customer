package com.xdisx.customer.app.service;

import com.xdisx.customer.api.dto.request.CustomerCreateRequestDto;
import com.xdisx.customer.api.dto.request.CustomerPageRequestDto;
import com.xdisx.customer.api.dto.response.CustomerPageResponseDto;
import com.xdisx.customer.api.dto.response.CustomerResponseDto;
import com.xdisx.customer.api.exception.CustomerCreateException;
import com.xdisx.customer.api.exception.CustomerNotFoundException;
import com.xdisx.customer.app.repository.db.CustomerRepository;
import com.xdisx.customer.app.repository.db.dto.CustomerPageDto;
import com.xdisx.customer.app.repository.db.entity.CustomerEntity;
import com.xdisx.customer.app.repository.db.filtering.CustomerSpecificationBuilder;
import com.xdisx.customer.app.service.converter.CustomerConverter;
import java.math.BigInteger;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {
  public static final String CUSTOMER_SAVE_ERROR = "Unable to save customer";
  private final CustomerRepository customerRepository;

  @Override
  @Transactional
  public CustomerResponseDto createCustomer(CustomerCreateRequestDto customerCreateRequestDto) {
    CustomerEntity customer = CustomerConverter.fromCreateRequest(customerCreateRequestDto);

    customer = saveAndFlushCustomer(customer);
    return CustomerConverter.toCustomerResponse(customer);
  }

  @Override
  @Transactional(readOnly = true)
  public CustomerPageResponseDto findCustomers(CustomerPageRequestDto customerPageRequestDto) {
    PageRequest pageRequest = CustomerConverter.toPageRequest(customerPageRequestDto);
    Specification<CustomerEntity> specifications =
        CustomerSpecificationBuilder.getFilterSpecifications(customerPageRequestDto);

    Page<CustomerPageDto> result =
        customerRepository.findBy(
            specifications,
            q -> q.as(CustomerPageDto.class).sortBy(pageRequest.getSort()).page(pageRequest));

    return new CustomerPageResponseDto(
        result.getTotalPages(),
        result.getTotalElements(),
        CustomerConverter.toListCustomerResponse(result));
  }

  @Override
  public CustomerResponseDto getCustomer(BigInteger id) {
    CustomerEntity customer =
        customerRepository
            .findById(id)
            .orElseThrow(
                () ->
                    new CustomerNotFoundException(
                        String.format("There is no customer with id %d", id)));
    return CustomerConverter.toCustomerResponse(customer);
  }

  private CustomerEntity saveAndFlushCustomer(CustomerEntity customer) {
    try {
      return customerRepository.saveAndFlush(customer);
    } catch (DataIntegrityViolationException e) {
      log.error("Unable to save customer with values {}", customer, e);
      throw new CustomerCreateException(CUSTOMER_SAVE_ERROR);
    }
  }
}
