package com.xdisx.customer.api;

import com.xdisx.customer.api.dto.request.CustomerCreateRequestDto;
import com.xdisx.customer.api.dto.request.CustomerPageRequestDto;
import com.xdisx.customer.api.dto.response.CustomerPageResponseDto;
import com.xdisx.customer.api.dto.response.CustomerResponseDto;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.math.BigInteger;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

public interface CustomerApi {
  String ROOT_PATH = "/xdisx";

  @PostMapping(ROOT_PATH + "/customer")
  @ResponseStatus(HttpStatus.CREATED)
  CustomerResponseDto createCustomer(
      @Valid @RequestBody CustomerCreateRequestDto customerCreateRequest);

  @GetMapping(ROOT_PATH + "/customers")
  @ResponseStatus(HttpStatus.OK)
  CustomerPageResponseDto getCustomers(
      @Valid @SpringQueryMap CustomerPageRequestDto customerPageRequestDto);

  @GetMapping(ROOT_PATH + "/customers/{id}")
  @ResponseStatus(HttpStatus.OK)
  CustomerResponseDto getCustomer(@PathVariable("id") @NotNull BigInteger id);
}
