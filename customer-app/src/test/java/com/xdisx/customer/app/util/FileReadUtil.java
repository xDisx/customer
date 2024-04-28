package com.xdisx.customer.app.util;
import lombok.experimental.UtilityClass;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import software.amazon.awssdk.utils.IoUtils;

@UtilityClass
public class FileReadUtil {
    public String readResourceAsString(String filePath) {
        try {
            var expectedResult = IoUtils.toUtf8String(new ClassPathResource(filePath).getInputStream());
            expectedResult = expectedResult.replace("\r\n", "");
            return expectedResult;
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }
}
