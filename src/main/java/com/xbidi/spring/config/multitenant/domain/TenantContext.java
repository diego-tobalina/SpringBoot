package com.xbidi.spring.config.multitenant.domain;

public abstract class TenantContext {
  private static final ThreadLocal<String> currentTenant = new InheritableThreadLocal<>();

  private TenantContext() {}

  public static String getCurrentTenant() {
    return currentTenant.get();
  }

  public static void setCurrentTenant(String tenant) {
    currentTenant.set(tenant);
  }

  public static void clear() {
    currentTenant.remove();
  }
}
