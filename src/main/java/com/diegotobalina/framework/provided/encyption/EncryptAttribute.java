package com.diegotobalina.framework.provided.encyption;

import com.diegotobalina.framework.provided.responses.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 * Attribute encryption
 *
 * @author diegotobalina
 */
@Slf4j
@Converter
public class EncryptAttribute implements AttributeConverter<String, String> {

  @SuppressWarnings({"java:S3008"})
  @Value("${encryption.key}")
  private static String KEY = "";

  @SuppressWarnings({"java:S3008"})
  @Value("${encryption.aad}")
  private static String AAD = "";

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
