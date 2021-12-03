package com.diegotobalina.framework.provided.multitenant;

import com.diegotobalina.framework.provided.Constants;
import com.diegotobalina.framework.provided.exception.exception.ForbiddenTenantException;
import com.diegotobalina.framework.provided.security.AuthenticationImpl;
import com.diegotobalina.framework.provided.security.AuthenticationManager;
import lombok.SneakyThrows;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class RequestInterceptor extends OncePerRequestFilter {
  private final AuthenticationManager authenticationManager;

  public RequestInterceptor(AuthenticationManager authenticationManager) {
    this.authenticationManager = authenticationManager;
  }

  @Override
  @SneakyThrows
  protected void doFilterInternal(
      HttpServletRequest req, @NotNull HttpServletResponse res, @NotNull FilterChain chain) {
    if (!req.getRequestURI().contains("/api")) {
      chain.doFilter(req, res);
      return;
    }
    String tenantID = req.getHeader(Constants.MULTITENANT_HEADER);
    String currentTenant = tenantID != null ? tenantID : Constants.MULTITENANT_DEFAULT_DB;
    if (authenticationManager.isAuthenticated()) {
      AuthenticationImpl authentication =
          (AuthenticationImpl) authenticationManager.getAuthenticated();
      if (!currentTenant.equals(Constants.MULTITENANT_DEFAULT_DB)
          && !authentication.hasTenant(currentTenant)) {
        throw new ForbiddenTenantException("Forbidden tenant for this user");
      }
    }
    TenantContext.setCurrentTenant(currentTenant);
    chain.doFilter(req, res);
    TenantContext.clear();
  }
}
