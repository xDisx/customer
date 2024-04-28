package com.xdisx.customer.e2e.customer.steps.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.xdisx.customer.api.dto.request.CustomerCreateRequestDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import static org.junit.jupiter.api.Assertions.fail;

import static com.xdisx.customer.e2e.common.utils.ReadFileUtil.readJson;

@Service
@RequiredArgsConstructor
@Slf4j
public class RequestBuilderServiceCustomer {
    private static final String JSON_READ_ERROR = "Failed to read the request JSON from %s";

    public CustomerCreateRequestDto buildCustomerCreateRequest() {
        var filePath = "/json/customer/CustomerCreateRequest.json";
        CustomerCreateRequestDto requestJson = null;

        try {
            requestJson = readJson(CustomerCreateRequestDto.class, filePath);
        } catch (JsonProcessingException e) {
            fail(String.format(JSON_READ_ERROR, filePath));
        }

        return requestJson;
    }
}
