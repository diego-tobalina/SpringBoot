package com.xbidi.spring.content.shared;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.enhanced.SequenceStyleGenerator;
import org.hibernate.internal.util.config.ConfigurationHelper;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.type.LongType;
import org.hibernate.type.Type;

import java.io.Serializable;
import java.util.Properties;

public class CustomSequenceStyleGenerator extends SequenceStyleGenerator {

  public static final String VALUE_PREFIX = "valuePrefix";
  public static final String VALUE_PREFIX_DEFAULT = "";

  public static final String NUMBER_FORMAT = "numberFormat";
  public static final String NUMBER_FORMAT_DEFAULT = "%d";

  private String numberFormat;
  private String valuePrefix;

  @Override
  public Serializable generate(SharedSessionContractImplementor session, Object object)
      throws HibernateException {
    return valuePrefix + String.format(numberFormat, super.generate(session, object));
  }

  @Override
  public void configure(Type type, Properties params, ServiceRegistry serviceRegistry) {
    super.configure(LongType.INSTANCE, params, serviceRegistry);
    valuePrefix = ConfigurationHelper.getString(VALUE_PREFIX, params, VALUE_PREFIX_DEFAULT);
    numberFormat = ConfigurationHelper.getString(NUMBER_FORMAT, params, NUMBER_FORMAT_DEFAULT);
  }
}
