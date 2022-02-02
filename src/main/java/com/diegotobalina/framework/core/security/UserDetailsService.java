package com.diegotobalina.framework.core.security;

/** @author diegotobalina created on 24/06/2020 */
public interface UserDetailsService {
  AuthenticationImpl getByToken(String token);
}
