package com.diegotobalina.framework.core.security;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author diegotobalina created on 24/06/2020
 */
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
                "default@gmail.com",
                new ArrayList<>(),
                new ArrayList<>(),
                List.of("default", "tenant1", "tenant2", "tenant3"),
                null,
                "default@bosonit.com",
                "default",
                token);
    }
}
