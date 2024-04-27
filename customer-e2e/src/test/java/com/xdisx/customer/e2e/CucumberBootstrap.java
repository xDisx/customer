package com.xdisx.customer.e2e;

import lombok.extern.slf4j.Slf4j;
import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.boot.test.context.SpringBootTest;


@Slf4j
@CucumberContextConfiguration
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        classes = ApplicationContext.class)
public class CucumberBootstrap {}
