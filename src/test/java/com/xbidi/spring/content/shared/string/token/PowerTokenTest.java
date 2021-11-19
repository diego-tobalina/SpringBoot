package com.xbidi.spring.content.shared.string.token;

import com.xbidi.spring.content.shared.PowerToken;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.regex.Pattern;

class PowerTokenTest {

  @Test
  void getFullToken() {
    PowerToken tokenByType = new PowerToken("type");
    String fullTokenByType = tokenByType.getFullToken();
    String regexUUID = "[0-9a-f]{8}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{12}:type";
    Pattern pattern = Pattern.compile(regexUUID);
    Assertions.assertTrue(pattern.matcher(fullTokenByType).matches());

    PowerToken tokenByTypeAndToken = new PowerToken("token", "type");
    String fullTokenByTypeAndToken = tokenByTypeAndToken.getFullToken();
    Assertions.assertEquals("token:type", fullTokenByTypeAndToken);
  }
}
