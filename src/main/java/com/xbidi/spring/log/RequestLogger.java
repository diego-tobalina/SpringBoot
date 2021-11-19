package com.xbidi.spring.log;

import com.xbidi.spring.content.shared.Constants;
import com.xbidi.spring.log.wrappers.BufferedRequestWrapper;
import com.xbidi.spring.log.wrappers.BufferedResponseWrapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import javax.servlet.FilterChain;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

/** @author diegotobalina created on 14/08/2020 */
@Slf4j
@Component
@AllArgsConstructor
public class RequestLogger {

  private final AsyncLogger asyncLogger;

  static String removeRowJumps(String string) {
    if (StringUtils.isBlank(string)) return null;
    return string.replace("\n", "").replace("\r", "");
  }

  public void log(ServletRequest request, ServletResponse response, FilterChain chain) {
    try {
      var httpServletRequest = (HttpServletRequest) request;
      var bufferedRequest = new BufferedRequestWrapper(httpServletRequest);
      var httpServletResponse = (HttpServletResponse) response;
      var bufferedResponse = new BufferedResponseWrapper(httpServletResponse);

      // log request
      long requestMillis = System.currentTimeMillis(); // time before request processing
      if (((HttpServletRequest) request).getRequestURI().contains("/api/"))
        logRequest(bufferedRequest);

      // process request
      chain.doFilter(bufferedRequest, bufferedResponse);

      // log response
      long responseMillis = System.currentTimeMillis(); // time after request processing
      long totalRequestMillis = responseMillis - requestMillis; // total request processing time
      if (((HttpServletRequest) request).getRequestURI().contains("/api/"))
        logResponse(bufferedResponse, totalRequestMillis);

    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public void logRequest(BufferedRequestWrapper bufferedRequest) throws IOException {
    String requestMethod = removeRowJumps(bufferedRequest.getMethod());
    String requestUri = removeRowJumps(bufferedRequest.getRequestURI());
    Map<String, String> requestMap = this.getParameters(bufferedRequest);
    String requestParams = removeRowJumps(requestMap.toString());
    String requestBody = removeRowJumps(bufferedRequest.getRequestBody());
    String requestHeaders = removeRowJumps(getHeaders(bufferedRequest).toString());

    asyncLogger.info(String.format(Constants.LOGS_REQUEST_METHOD, requestMethod));
    asyncLogger.info(String.format(Constants.LOGS_REQUEST_URL, requestUri));
    asyncLogger.info(String.format(Constants.LOGS_REQUEST_PARAMS, requestParams));
    asyncLogger.info(String.format(Constants.LOGS_REQUEST_BODY, requestBody));
    asyncLogger.info(String.format(Constants.LOGS_REQUEST_HEADERS, requestHeaders));
  }

  public void logResponse(BufferedResponseWrapper bufferedResponse, long totalRequestTime) {
    int responseStatus = bufferedResponse.getStatus();
    String responseHeaders = removeRowJumps(getHeaders(bufferedResponse).toString());
    String responseBody = bufferedResponse.getContent();

    asyncLogger.info(String.format(Constants.LOGS_RESPONSE_CODE, responseStatus));
    asyncLogger.info(String.format(Constants.LOGS_RESPONSE_HEADERS, responseHeaders));
    asyncLogger.info(String.format(Constants.LOGS_RESPONSE_BODY, removeRowJumps(responseBody)));
    asyncLogger.info(String.format(Constants.LOGS_TOTAL_REQUEST_TIME, totalRequestTime));
  }

  private List<String> getHeaders(HttpServletRequest httpServletRequest) {
    Enumeration<String> headerNames = httpServletRequest.getHeaderNames();
    List<String> headers = new ArrayList<>();
    if (headerNames != null) {
      while (headerNames.hasMoreElements()) {
        String headerName = headerNames.nextElement();
        String headerValue = httpServletRequest.getHeader(headerName);
        headers.add(String.format("%s : %s", headerName, headerValue));
      }
    }
    return headers;
  }

  private List<String> getHeaders(HttpServletResponse httpServletResponse) {
    Collection<String> headerNames = httpServletResponse.getHeaderNames();
    List<String> headers = new ArrayList<>();
    for (String headerName : headerNames) {
      String headerValue = httpServletResponse.getHeader(headerName);
      headers.add(String.format("%s : %s", headerName, headerValue));
    }
    return headers;
  }

  private Map<String, String> getParameters(HttpServletRequest request) {
    Map<String, String> typesafeRequestMap = new HashMap<>();
    Enumeration<?> requestParamNames = request.getParameterNames();
    while (requestParamNames.hasMoreElements()) {
      String requestParamName = (String) requestParamNames.nextElement();
      String requestParamValue = request.getParameter(requestParamName);
      typesafeRequestMap.put(requestParamName, requestParamValue);
    }
    return typesafeRequestMap;
  }
}
