package com.xbidi.spring.security.user.details;

import com.xbidi.spring.security.authentication.AuthenticationImpl;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

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
    return new AuthenticationImpl(
        "anonymous@gmail.com",
        new ArrayList<>(),
        new ArrayList<>(),
        new ArrayList<>(),
        null,
        "anonymous@gmail.com",
        "anonymous",
        token);
  }
}
