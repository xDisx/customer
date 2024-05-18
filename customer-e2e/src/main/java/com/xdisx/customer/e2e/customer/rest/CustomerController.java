package com.xdisx.customer.e2e.customer.rest;

import com.xdisx.customer.api.CustomerApi;
import com.xdisx.customer.e2e.config.FeignConfig;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(
    contextId = "customerService",
    name = "${xdisx.service.customer.client-name}",
    url = "${xdisx.service.customer.url}",
    configuration = FeignConfig.class)
public interface CustomerController extends CustomerApi {}
