package com.diegotobalina.framework.core.security;

import org.springframework.context.annotation.Profile;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.io.Serializable;

/**
 * @author diegotobalina created on 24/06/2020
 */
@Component
@Profile("!local") // solo comprueba los permisos cuando no estás trabajando en local
public class PermissionEvaluatorImpl implements PermissionEvaluator {

  @Override
  public boolean hasPermission(
      Authentication authentication, Object accessType, Object requiredPermission) {
    var requiredPermissionString = requiredPermission.toString();
    var authenticationImpl = (AuthenticationImpl) authentication.getPrincipal();
    return authenticationImpl.hasCredential(requiredPermissionString);
  }

  @Override
  public boolean hasPermission(
      Authentication authentication,
      Serializable serializable,
      String targetType,
      Object permission) {
    var requiredPermissionString = permission.toString();
    var authenticationImpl = (AuthenticationImpl) authentication.getPrincipal();
    return authenticationImpl.hasCredential(requiredPermissionString);
  }
}
