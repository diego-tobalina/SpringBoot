package com.xbidi.spring.config.multitenant.domain;

import com.xbidi.spring.content.shared.Constants;
import org.hibernate.context.spi.CurrentTenantIdentifierResolver;
import org.springframework.stereotype.Component;

@Component
public class TenantResolver implements CurrentTenantIdentifierResolver {

  @Override
  public String resolveCurrentTenantIdentifier() {
    String t = TenantContext.getCurrentTenant();
    return t != null ? t : Constants.MULTITENANT_DEFAULT_DB;
  }

  @Override
  public boolean validateExistingCurrentSessions() {
    return true;
  }
}
