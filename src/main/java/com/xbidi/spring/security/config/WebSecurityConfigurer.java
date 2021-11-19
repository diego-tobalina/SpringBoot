package com.xbidi.spring.security.config;

import com.xbidi.spring.content.shared.output.ErrorResponse;
import com.xbidi.spring.content.shared.output.PowerResponse;
import com.xbidi.spring.security.authentication.AuthenticationFilter;
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

/** @author diegotobalina created on 24/06/2020 */
@Slf4j
@EnableWebSecurity
@AllArgsConstructor
public class WebSecurityConfigurer extends WebSecurityConfigurerAdapter {

  private final AuthenticationFilter authenticationFilter;

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.csrf().disable();
    http.httpBasic().disable();
    http.formLogin().disable();
    http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    http.exceptionHandling().authenticationEntryPoint(this::unauthorized);

    http.antMatcher("/api/**")
        .authorizeRequests()
        .anyRequest()
        .authenticated()
        .and()
        .addFilterBefore(authenticationFilter, AnonymousAuthenticationFilter.class);
  }

  private void unauthorized(
      HttpServletRequest req, HttpServletResponse res, AuthenticationException e)
      throws IOException {
    var errorResponse = new ErrorResponse(e, 401);
    errorResponse.printMessage();
    new PowerResponse(res).sendJson(errorResponse, HttpStatus.UNAUTHORIZED.value());
  }
}
