package com.diegotobalina.framework.provided.multitenant;

import org.hibernate.context.spi.CurrentTenantIdentifierResolver;
import org.springframework.stereotype.Component;

@Component
public class TenantResolver implements CurrentTenantIdentifierResolver {

  @Override
  public String resolveCurrentTenantIdentifier() {
    String currentTenant = TenantContext.getCurrentTenant();
    return currentTenant != null ? currentTenant : "default";
  }

  @Override
  public boolean validateExistingCurrentSessions() {
    return true;
  }
}
