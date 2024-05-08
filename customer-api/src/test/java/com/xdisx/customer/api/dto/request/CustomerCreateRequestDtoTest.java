package com.xdisx.customer.api.dto.request;

import com.xdisx.customer.api.utils.ObjectDeserializerUtils;
import java.io.IOException;
import org.json.JSONException;
import org.junit.jupiter.api.Test;

class CustomerCreateRequestDtoTest {
    private final ObjectDeserializerUtils utils = new ObjectDeserializerUtils();

    @Test
    void deserializeCreateCustomerRequest() throws JSONException, IOException {
        utils.assertSerialization(
                "CustomerCreateRequest.json", CustomerCreateRequestDto.class);
    }
}
