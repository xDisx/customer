package com.xdisx.customer.app.service;

import com.xdisx.customer.api.dto.request.CustomerCreateRequestDto;
import com.xdisx.customer.api.dto.request.CustomerPageRequestDto;
import com.xdisx.customer.api.dto.response.CustomerPageResponseDto;
import com.xdisx.customer.api.dto.response.CustomerResponseDto;
import com.xdisx.customer.api.exception.CustomerCreateException;
import com.xdisx.customer.app.repository.contract.ContractRepository;
import com.xdisx.customer.app.repository.db.CustomerRepository;
import com.xdisx.customer.app.repository.db.dto.CustomerPageDto;
import com.xdisx.customer.app.repository.db.entity.CustomerEntity;
import com.xdisx.customer.app.repository.db.filtering.CustomerSpecificationBuilder;
import com.xdisx.customer.app.service.converter.CustomerConverter;
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
  private final ContractRepository contractRepository;

  @Override
  @Transactional
  public CustomerResponseDto createCustomer(CustomerCreateRequestDto customerCreateRequestDto) {
    CustomerEntity customer = CustomerConverter.fromCreateRequest(customerCreateRequestDto);

    customer = saveAndFlushCustomer(customer);
//    log.info("Customer created: {}", customer);
//    log.info("Calling contract");
//    ContractResponseDto contract =
//        contractRepository.createContract(
//            ContractCreateRequestDto.builder()
//                .contractType("Sall from customer")
//                .customerId(BigInteger.ONE)
//                .build());
//    log.info("Contract created: {}", contract);
    return CustomerConverter.toCustomerResponse(customer);
  }

  @Override
  public CustomerPageResponseDto findCustomers(CustomerPageRequestDto customerPageRequestDto) {
    PageRequest pageRequest =
            CustomerConverter.toPageRequest(customerPageRequestDto);
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

  private CustomerEntity saveAndFlushCustomer(CustomerEntity customer) {
    try {
      return customerRepository.saveAndFlush(customer);
    } catch (DataIntegrityViolationException e) {
      log.error("Unable to save customer with values {}", customer, e);
      throw new CustomerCreateException(CUSTOMER_SAVE_ERROR);
    }
  }
}
