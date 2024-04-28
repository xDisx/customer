package com.xdisx.customer.api.dto.response;

import com.xdisx.customer.api.utils.ObjectDeserializerUtils;
import org.json.JSONException;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class CustomerResponseDtoTest {
    private final ObjectDeserializerUtils utils = new ObjectDeserializerUtils();

    @Test
    void deserializeCustomerResponse() throws JSONException, IOException {
        utils.assertSerialization(
                "CustomerResponse.json", CustomerResponseDto.class);
    }

}
