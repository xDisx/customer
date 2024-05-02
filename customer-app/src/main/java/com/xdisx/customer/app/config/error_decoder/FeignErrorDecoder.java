package com.xdisx.customer.app.config.error_decoder;

import com.xdisx.customer.app.config.error_decoder.exception.FeignException;
import feign.Response;
import feign.codec.ErrorDecoder;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FeignErrorDecoder implements ErrorDecoder {
  @Override
  public Exception decode(String s, Response response) {
    switch (response.status()) {
      case 404, 403 ->
          log.warn(
              "Request error at URL: {}, with status: {}",
              response.request().url(),
              response.status());

      case 400 ->
          log.warn(
              "Request error at URL: {}, with status: {}, with body {}",
              response.request().url(),
              response.status(),
              response.body());
      default ->
          log.error(
              "Request error at URL: {}, with status: {}",
              response.request().url(),
              response.status());
    }
    return new FeignException("Request failed with status: " + response.status());
  }
}
