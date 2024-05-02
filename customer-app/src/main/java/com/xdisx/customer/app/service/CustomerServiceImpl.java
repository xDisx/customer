package com.xdisx.customer.app.service;

import com.xdisx.contract.api.dto.request.ContractCreateRequestDto;
import com.xdisx.contract.api.dto.response.ContractResponseDto;
import com.xdisx.customer.api.dto.request.CustomerCreateRequestDto;
import com.xdisx.customer.api.dto.response.CustomerResponseDto;
import com.xdisx.customer.api.exception.CustomerCreateException;
import com.xdisx.customer.app.repository.contract.ContractRepository;
import com.xdisx.customer.app.repository.db.CustomerRepository;
import com.xdisx.customer.app.repository.db.entity.CustomerEntity;
import com.xdisx.customer.app.service.converter.CustomerConverter;
import java.math.BigInteger;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {
  private static final String CUSTOMER_SAVE_ERROR = "Unable to save customer";
  private final CustomerRepository customerRepository;
  private final ContractRepository contractRepository;

  @Override
  @Transactional
  public CustomerResponseDto createCustomer(CustomerCreateRequestDto customerCreateRequestDto) {
    CustomerEntity customer = CustomerConverter.fromCreateRequest(customerCreateRequestDto);

    customer = saveAndFlushCustomer(customer);
    log.info("Customer created: {}", customer);
    log.info("Calling contract");
    ContractResponseDto contract =
        contractRepository.createContract(
            ContractCreateRequestDto.builder().contractType("Sall from customer").customerId(BigInteger.ONE).build());
    log.info("Contract created: {}", contract);
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
