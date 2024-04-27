package com.xdisx.customer.e2e.customer.rest;

import com.xdisx.customer.api.CustomerApi;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(
        contextId = "customerService",
        name = "${xdisx.service.customer.client-name}",
        url = "${xdisx.service.customer.url}")
public interface CustomerController extends CustomerApi {}
