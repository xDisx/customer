package com.xdisx.customer.api.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.json.JSONException;
import org.junit.jupiter.api.Assertions;
import org.skyscreamer.jsonassert.JSONCompare;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.skyscreamer.jsonassert.JSONCompareResult;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class ObjectDeserializerUtils {
    private final ObjectMapper mapper = mapper();

    private ObjectMapper mapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        return objectMapper;
    }

    public void assertSerialization(String path, Class<?> serialClass) throws IOException, JSONException {
        String expected = new String(Files.readAllBytes(Paths.get("src/test/resources", path)));
        Object object = mapper.readValue(expected, serialClass);
        String actual = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(object);
        JSONCompareResult result = JSONCompare.compareJSON(actual, expected, JSONCompareMode.LENIENT);
        Assertions.assertTrue(result.passed(), result.getMessage());
    }

}
