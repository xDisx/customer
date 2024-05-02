package com.xdisx.customer.app.repository.contract;

import com.xdisx.contract.api.ContractApi;
import com.xdisx.customer.app.config.error_decoder.FeignErrorDecoder;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(
    name = "${spring.xdisx.customer.contract.client-name}",
    url = "${xdisx.customer.contract.url}",
    configuration = FeignErrorDecoder.class)
public interface ContractRepository extends ContractApi {}
