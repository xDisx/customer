package com.xdisx.customer.app.rest;

import com.xdisx.customer.api.CustomerApi;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class CustomerController implements CustomerApi {
    @Override
    public String salut(Integer numar) {
        log.info("am primit API call cu " + numar);
        Integer nr2 = numar * 2;
        return nr2.toString();
    }
}
