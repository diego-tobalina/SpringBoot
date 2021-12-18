package com.diegotobalina.framework.provided.multitenant;

import com.diegotobalina.framework.provided.exception.exception.ForbiddenTenantException;
import com.diegotobalina.framework.provided.responses.ErrorResponse;
import com.diegotobalina.framework.provided.responses.PowerResponse;
import com.diegotobalina.framework.provided.security.AuthenticationImpl;
import com.diegotobalina.framework.provided.security.AuthenticationManager;
import lombok.SneakyThrows;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpStatus;
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

    // solo las rutas que contengan /api tienen filtro de tenant
    if (!req.getRequestURI().contains("/api")) {
      chain.doFilter(req, res);
      return;
    }


    // comprueba que el usuario pueda acceder al tenant
    String tenantID = req.getHeader("X-Tenant-Id");
    if (authenticationManager.isAuthenticated()) {
      AuthenticationImpl authentication = (AuthenticationImpl) authenticationManager.getAuthenticated();
      if (!authentication.hasTenant(tenantID)) {
        int unauthorizedStatus = HttpStatus.FORBIDDEN.value();
        ForbiddenTenantException forbiddenTenantException = new ForbiddenTenantException("Forbidden tenant for this user");
        ErrorResponse errorResponse = new ErrorResponse(forbiddenTenantException, unauthorizedStatus).printMessage();
        new PowerResponse(res).sendJson(errorResponse, unauthorizedStatus);
        return;
      }
    }

    // cambia el contexto con el nuevo tenant
    TenantContext.setCurrentTenant(tenantID);
    chain.doFilter(req, res);
    TenantContext.clear();
  }

}
