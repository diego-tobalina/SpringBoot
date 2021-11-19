package com.xbidi.spring.security.authentication;

import lombok.Getter;
import lombok.ToString;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@ToString
public class AuthenticationImpl implements Authentication {

  @Getter private final String userId;
  @Getter private final String authorization;
  private final String email;
  private final transient Object principal;
  private final transient Object details;
  private final transient List<String> credentials;
  private final transient List<String> tenants;
  private final List<GrantedAuthority> roles;
  private boolean authenticated;

  /** @param roles Must follow this patter: ['ROLE_USER','ROLE_ADMIN'...] */
  @SuppressWarnings({"java:S107"})
  public AuthenticationImpl(
      Object principal,
      List<String> roles,
      List<String> credentials,
      List<String> tenants,
      Object details,
      String email,
      String userId,
      String authorization) {

    this.credentials = credentials;
    this.tenants = tenants;
    this.details = details;
    this.principal = principal;
    this.authenticated = true;
    this.roles = getRoles(roles);
    this.email = email;
    this.userId = userId;
    this.authorization = authorization;
  }

  private List<GrantedAuthority> getRoles(List<String> authorities) {
    return authorities.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return roles;
  }

  @Override
  public List<String> getCredentials() {
    return this.credentials;
  }

  @Override
  public Object getDetails() {
    return this.details;
  }

  @Override
  public Object getPrincipal() {
    return this.principal;
  }

  @Override
  public boolean isAuthenticated() {
    return this.authenticated;
  }

  @Override
  public void setAuthenticated(boolean authenticated) throws IllegalArgumentException {
    this.authenticated = authenticated;
  }

  @Override
  public String getName() {
    return this.email;
  }

  public boolean hasCredential(String requiredPermissionString) {
    if (this.credentials == null) return false;
    return this.credentials.contains(requiredPermissionString);
  }

  public boolean hasTenant(String tenant) {
    return tenants.contains(tenant);
  }
}
