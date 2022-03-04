package com.diegotobalina.framework.core.multitenant;

import com.diegotobalina.framework.core.api.response.ErrorResponse;
import com.diegotobalina.framework.core.api.response.PowerResponse;
import com.diegotobalina.framework.core.exception.exception.ForbiddenTenantException;
import com.diegotobalina.framework.core.exception.exception.MissingTenantException;
import com.diegotobalina.framework.core.security.AuthenticationImpl;
import com.diegotobalina.framework.core.security.AuthenticationManager;
import lombok.SneakyThrows;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class TenantFilter extends OncePerRequestFilter {

  private final AuthenticationManager authenticationManager;

  public TenantFilter(AuthenticationManager authenticationManager) {
    this.authenticationManager = authenticationManager;
  }

  @Override
  @SneakyThrows
  protected void doFilterInternal(
      HttpServletRequest req, @NotNull HttpServletResponse res, @NotNull FilterChain chain) {

    // solo las rutas que contengan /api tienen filtro de tenant
    if (!req.getRequestURI().contains("/api/")) {
      chain.doFilter(req, res);
      return;
    }

    // comprueba que el usuario ha elegido un tenant (tenant por defecto: "default")
    String tenantID = req.getHeader("X-Tenant-Id");
    if (tenantID == null || tenantID.isBlank()) {
      sendError(
          res, HttpStatus.UNAUTHORIZED, new MissingTenantException("Missing X-Tenant-Id Header"));
      return;
    }

    // comprueba que el usuario pueda acceder al tenant
    if (authenticationManager.isAuthenticated()) {
      AuthenticationImpl authentication =
          (AuthenticationImpl) authenticationManager.getAuthenticated();
      if (!authentication.hasTenant(tenantID)) {
        sendError(
            res,
            HttpStatus.FORBIDDEN,
            new ForbiddenTenantException("Forbidden tenant for this user"));
        return;
      }
    }

    // cambia el contexto con el nuevo tenant
    TenantContext.setCurrentTenant(tenantID);
    chain.doFilter(req, res);
    TenantContext.clear();
  }

  private void sendError(
      @NotNull HttpServletResponse res, HttpStatus badRequest, RuntimeException runtimeException)
      throws IOException {
    int badRequestStatus = badRequest.value();
    ErrorResponse errorResponse =
        new ErrorResponse(runtimeException, badRequestStatus).printMessage();
    new PowerResponse(res).sendJson(errorResponse, badRequestStatus);
  }
}
