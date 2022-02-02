package com.diegotobalina.framework.core.security;

import com.diegotobalina.framework.core.api.response.ErrorResponse;
import com.diegotobalina.framework.core.api.response.PowerResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AnonymousAuthenticationFilter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author diegotobalina created on 24/06/2020
 */
@Slf4j
@EnableWebSecurity
@AllArgsConstructor
public class WebSecurityConfigurer extends WebSecurityConfigurerAdapter {

    private final AuthenticationFilter authenticationFilter;
    private static final String SECURED_REGEX = "/api/**";

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.httpBasic().disable();
        http.formLogin().disable();
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.exceptionHandling().authenticationEntryPoint(this::unauthorized);

        http.antMatcher(SECURED_REGEX)
                .authorizeRequests()
                .anyRequest()
                .authenticated()
                .and()
                .addFilterBefore(authenticationFilter, AnonymousAuthenticationFilter.class);
    }

    private void unauthorized(
            HttpServletRequest req, HttpServletResponse res, AuthenticationException e)
            throws IOException {
        int unauthorizedStatus = HttpStatus.UNAUTHORIZED.value();
        ErrorResponse errorResponse = new ErrorResponse(e, unauthorizedStatus).printMessage();
        new PowerResponse(res).sendJson(errorResponse, unauthorizedStatus);
    }
}
