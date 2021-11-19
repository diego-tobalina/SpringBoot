package com.xbidi.spring.log;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import javax.servlet.*;

/** @author diegotobalina created on 24/06/2020 */
@Component
@AllArgsConstructor
public class RequestFilter implements Filter {

  private final RequestLogger requestLogger;

  @Override
  public void init(FilterConfig filterConfig) { // empty
  }

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) {
    requestLogger.log(request, response, chain);
  }

  @Override
  public void destroy() { // empty
  }
}
