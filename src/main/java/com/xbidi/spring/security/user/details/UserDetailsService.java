package com.xbidi.spring.security.user.details;

import com.xbidi.spring.security.authentication.AuthenticationImpl;

/** @author diegotobalina created on 24/06/2020 */
public interface UserDetailsService {
  AuthenticationImpl getByToken(String token);
}
