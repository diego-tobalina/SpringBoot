package com.xbidi.spring.security.user.details;

import com.xbidi.spring.security.authentication.AuthenticationImpl;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/** @author diegotobalina created on 24/06/2020 */
@Slf4j
@Component
@AllArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

  /**
   * @param token Authorization header, will never be null or empty
   * @return AuthenticationImpl if token verification goes ok or null if something went wrong
   */
  @Override
  public AuthenticationImpl getByToken(String token) {
    var email = "dev@spring.com";
    var userId = "USU00001_DEV";
    List<String> roles = new ArrayList<>();
    List<String> credentials = new ArrayList<>();
    List<String> tenants = new ArrayList<>();
    return new AuthenticationImpl(email, credentials, roles, tenants, null, email, userId, token);
  }
}
