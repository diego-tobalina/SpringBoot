package com.xbidi.spring.security.authentication;

import com.xbidi.spring.security.user.details.UserDetailsService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static com.xbidi.spring.content.shared.Constants.ANONYMOUS_USER;
import static com.xbidi.spring.content.shared.Constants.AUTHORIZATION_HEADER;

/** @author diegotobalina created on 24/06/2020 */
@Component
public class AuthenticationManager {

  private final UserDetailsService userDetailsService;

  public AuthenticationManager(UserDetailsService userDetailsService) {
    this.userDetailsService = userDetailsService;
  }

  public boolean isAuthenticated() {
    var context = SecurityContextHolder.getContext();
    var authentication = context.getAuthentication();
    if (authentication == null) return false;
    if (!authentication.isAuthenticated()) return false;
    return !authentication.getPrincipal().equals(ANONYMOUS_USER);
  }

  public void authenticate(HttpServletRequest req) {
    if (isAuthenticated()) return;
    // Verify the token
    String authorization = req.getHeader(AUTHORIZATION_HEADER);
    AuthenticationImpl userDetails = this.userDetailsService.getByToken(authorization);
    if (userDetails == null) return;

    // Authenticate the user
    var securityContext = SecurityContextHolder.getContext();
    securityContext.setAuthentication(userDetails);

    // Create a new session and add the security context.
    HttpSession session = req.getSession(true);
    session.setAttribute("SPRING_SECURITY_CONTEXT", securityContext);
  }

  public Authentication getAuthenticated() {
    var context = SecurityContextHolder.getContext();
    return context.getAuthentication();
  }
}
