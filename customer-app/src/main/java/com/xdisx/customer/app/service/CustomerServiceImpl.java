package com.xdisx.customer.app.service;

import com.xdisx.customer.api.dto.request.CustomerCreateRequestDto;
import com.xdisx.customer.api.dto.response.CustomerResponseDto;
import com.xdisx.customer.api.exception.CustomerCreateException;
import com.xdisx.customer.app.repository.db.CustomerRepository;
import com.xdisx.customer.app.repository.db.entity.CustomerEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.xdisx.customer.app.service.converter.CustomerConverter;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {
    private static final String CUSTOMER_SAVE_ERROR = "Unable to save customer";
    private final CustomerRepository customerRepository;

    @Override
    @Transactional
    public CustomerResponseDto createCustomer(CustomerCreateRequestDto customerCreateRequestDto) {
        CustomerEntity customer = CustomerConverter.fromCreateRequest(customerCreateRequestDto);

        customer = saveAndFlushCustomer(customer);
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
