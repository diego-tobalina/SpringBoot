package com.xbidi.spring.config;

import com.xbidi.spring.content.shared.Constants;
import com.xbidi.spring.security.authentication.AuthenticationImpl;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

public class AuditorAwareImpl implements AuditorAware<String> {

  @Override
  public Optional<String> getCurrentAuditor() {
    if (!isAuthenticated()) return Optional.of(Constants.ANONYMOUS_USER);
    Authentication authenticated = getAuthenticated();
    AuthenticationImpl customUserDetails = (AuthenticationImpl) authenticated;
    return Optional.of(customUserDetails.getUserId());
  }

  public boolean isAuthenticated() {
    var context = SecurityContextHolder.getContext();
    var authentication = context.getAuthentication();
    if (authentication == null) return false;
    if (!authentication.isAuthenticated()) return false;
    return !authentication.getPrincipal().equals(Constants.ANONYMOUS_USER);
  }

  public Authentication getAuthenticated() {
    var context = SecurityContextHolder.getContext();
    return context.getAuthentication();
  }
}
