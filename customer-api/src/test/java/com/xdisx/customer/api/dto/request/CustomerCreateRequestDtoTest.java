package com.xdisx.customer.api.dto.request;

import com.xdisx.customer.api.utils.ObjectDeserializerUtils;
import org.json.JSONException;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class CustomerCreateRequestDtoTest {
    private final ObjectDeserializerUtils utils = new ObjectDeserializerUtils();

    @Test
    void deserializeCreateCustomerRequest() throws JSONException, IOException {
        utils.assertSerialization(
                "CustomerCreateRequest.json", CustomerCreateRequestDto.class);
    }
}
