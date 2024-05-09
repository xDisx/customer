package com.xdisx.customer.app.rest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.xdisx.customer.api.dto.request.CustomerCreateRequestDto;
import com.xdisx.customer.api.dto.request.CustomerPageRequestDto;
import com.xdisx.customer.api.dto.response.CustomerPageResponseDto;
import com.xdisx.customer.api.dto.response.CustomerResponseDto;
import com.xdisx.customer.app.mock.CustomerMock;
import com.xdisx.customer.app.service.CustomerService;
import com.xdisx.customer.app.util.FileReadUtil;
import java.text.SimpleDateFormat;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ExtendWith(MockitoExtension.class)
class CustomerControllerTest {
  private static final ObjectMapper mapper =
      new ObjectMapper()
          .registerModule(new JavaTimeModule())
          .setDateFormat(new SimpleDateFormat("dd.MM.yyyy"));
  private static final String CUSTOMER_PATH = "/xdisx/customer";
  private static final String CUSTOMER_RESPONSE_JSON = "CustomerResponse.json";
  private static final String CUSTOMERS_PATH = "/xdisx/customers";
  private static final String CUSTOMERS_RESPONSE_JSON = "CustomersResponse.json";
  @Mock private CustomerService customerService;

  @InjectMocks private CustomerController classUnderTest;
  private MockMvc mockMvc;

  @BeforeEach
  public void setUp() {
    MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter =
        new MappingJackson2HttpMessageConverter();
    mappingJackson2HttpMessageConverter.setObjectMapper(mapper);

    mockMvc =
        MockMvcBuilders.standaloneSetup(classUnderTest)
            .setMessageConverters(mappingJackson2HttpMessageConverter)
            .setControllerAdvice(CustomerControllerExceptionHandler.class)
            .build();
  }

  @Test
  void createCustomer() throws Exception {
    CustomerCreateRequestDto customerRequest = CustomerMock.getCreateCustomerRequest();
    CustomerResponseDto response = CustomerMock.getCustomerResponse();

    when(customerService.createCustomer(customerRequest)).thenReturn(response);

    var apiResponse =
        mockMvc
            .perform(
                post(CUSTOMER_PATH)
                    .content(mapper.writeValueAsString(customerRequest))
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isCreated())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andReturn()
            .getResponse()
            .getContentAsString();

    var expectedResponse = FileReadUtil.readResourceAsString(CUSTOMER_RESPONSE_JSON);
    JSONAssert.assertEquals(expectedResponse, apiResponse, JSONCompareMode.LENIENT);
  }

  @Test
  void getCustomers() throws Exception {
    CustomerPageResponseDto responseDto = CustomerMock.getCustomerPageResponse();
    when(customerService.findCustomers(any(CustomerPageRequestDto.class))).thenReturn(responseDto);

    var apiResponse =
        mockMvc
            .perform(
                get(CUSTOMERS_PATH)
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andReturn()
            .getResponse()
            .getContentAsString();

    assertNotNull(apiResponse);
    var expectedResponse = FileReadUtil.readResourceAsString(CUSTOMERS_RESPONSE_JSON);
    JSONAssert.assertEquals(expectedResponse, apiResponse, JSONCompareMode.LENIENT);
  }

  @Test
  void getCustomer() throws Exception {
    CustomerResponseDto responseDto = CustomerMock.getCustomerResponse();
    when(customerService.getCustomer(responseDto.getID())).thenReturn(responseDto);

    var apiResponse =
        mockMvc
            .perform(
                get(CUSTOMERS_PATH + "/" + responseDto.getID())
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andReturn()
            .getResponse()
            .getContentAsString();

    assertNotNull(apiResponse);
    var expectedResponse = FileReadUtil.readResourceAsString(CUSTOMER_RESPONSE_JSON);
    JSONAssert.assertEquals(expectedResponse, apiResponse, JSONCompareMode.LENIENT);
  }
}
