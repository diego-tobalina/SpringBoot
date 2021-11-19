package com.xbidi.spring.content.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.xbidi.spring.content.shared.output.ErrorResponse;
import org.junit.jupiter.api.Assertions;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MvcResult;

import java.io.UnsupportedEncodingException;

public class TestUtil {
  public static void assertExceptionMessage(MvcResult mvcResult, String expectedMessage)
      throws UnsupportedEncodingException, JsonProcessingException {
    // obtener el mensaje de error desde el body
    MockHttpServletResponse response = mvcResult.getResponse();
    String body = response.getContentAsString();
    ErrorResponse errorResponse = new ObjectMapper().readValue(body, ErrorResponse.class);

    // comprobar que el mensaje de error no ha cambiado
    String actual = errorResponse.getMessage();
    Assertions.assertEquals(expectedMessage, actual);
  }
}
