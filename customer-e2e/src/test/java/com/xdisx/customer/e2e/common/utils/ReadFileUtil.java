package com.xdisx.customer.e2e.common.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.micrometer.core.instrument.util.IOUtils;
import lombok.experimental.UtilityClass;

@UtilityClass
public class ReadFileUtil {
    private final ObjectMapper objectMapper =
            JsonMapper.builder().addModule(new JavaTimeModule()).build();

    public static <T> T readJson(Class<T> aClass, String filePath) throws JsonProcessingException {
        var expectedResult = IOUtils.toString(aClass.getResourceAsStream(filePath));
        expectedResult = expectedResult.replace("\n", "");

        return objectMapper.readValue(expectedResult, aClass);
    }
}
