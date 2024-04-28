package com.xdisx.customer.api;

import com.xdisx.customer.api.dto.request.CustomerCreateRequestDto;
import com.xdisx.customer.api.dto.response.CustomerResponseDto;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

public interface CustomerApi {
    String ROOT_PATH = "/xdisx";

    @PostMapping(ROOT_PATH + "/customer")
    @ResponseStatus(HttpStatus.CREATED)
    CustomerResponseDto createCustomer(@Valid @RequestBody CustomerCreateRequestDto customerCreateRequest);

}
