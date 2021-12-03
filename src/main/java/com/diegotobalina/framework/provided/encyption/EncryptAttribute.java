package com.diegotobalina.framework.provided.encyption;

import com.diegotobalina.framework.provided.responses.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.persistence.AttributeConverter;

/**
 * Attribute encryption
 *
 * @author diegotobalina
 */
@Slf4j
@Component
public class EncryptAttribute implements AttributeConverter<String, String> {

  @Value("${encryption.key}")
  private static String KEY = null;

  @Value("${encryption.aad}")
  private static String AAD = null;

  private static final EncryptManager encryptManager = new EncryptManager(KEY.getBytes(), AAD);

  @Override
  public String convertToDatabaseColumn(String attribute) {
    try {
      return encryptManager.encrypt(attribute);
    } catch (Exception ex) {
      new ErrorResponse(ex, 500).printMessage();
    }
    return null;
  }

  @Override
  public String convertToEntityAttribute(String dbData) {
    try {
      return encryptManager.decrypt(dbData);
    } catch (Exception ex) {
      new ErrorResponse(ex, 500).printMessage();
    }
    return null;
  }
}
