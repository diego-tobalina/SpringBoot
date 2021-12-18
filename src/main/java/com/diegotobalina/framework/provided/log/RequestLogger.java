package com.diegotobalina.framework.provided.log;

import com.diegotobalina.framework.provided.Constants;
import com.diegotobalina.framework.provided.log.wrappers.BufferedRequestWrapper;
import com.diegotobalina.framework.provided.log.wrappers.BufferedResponseWrapper;
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
public class RequestLogger {

  private final AsyncLogger asyncLogger;
  private static final boolean LOG_REQUEST_BODY = true;
  private static final boolean LOG_RESPONSE_BODY = true;

  public RequestLogger(AsyncLogger asyncLogger) {
    this.asyncLogger = asyncLogger;
  }

  public void log(ServletRequest request, ServletResponse response, FilterChain chain) {
    try {
      var httpServletRequest = (HttpServletRequest) request;
      var bufferedRequest = new BufferedRequestWrapper(httpServletRequest);
      var httpServletResponse = (HttpServletResponse) response;
      var bufferedResponse = new BufferedResponseWrapper(httpServletResponse);

      // log request
      String requestLog = null;
      long requestMillis = System.currentTimeMillis(); // time before request processing
      if (((HttpServletRequest) request).getRequestURI().contains("/api/"))
        requestLog = logRequest(bufferedRequest);

      // process request
      chain.doFilter(bufferedRequest, bufferedResponse);

      // log response
      String responseLog = null;
      long responseMillis = System.currentTimeMillis(); // time after request processing
      long totalRequestMillis = responseMillis - requestMillis; // total request processing time
      if (((HttpServletRequest) request).getRequestURI().contains("/api/"))
        responseLog = logResponse(bufferedResponse, totalRequestMillis);

      if (requestLog != null && responseLog != null) {
        String log = String.format("%s%s", requestLog, responseLog);
        asyncLogger.info(log);
      }

    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public String logRequest(BufferedRequestWrapper bufferedRequest) throws IOException {
    String requestMethod = removeRowJumps(bufferedRequest.getMethod());
    String requestUri = removeRowJumps(bufferedRequest.getRequestURI());
    Map<String, String> requestMap = this.getParameters(bufferedRequest);
    String requestParams = removeRowJumps(requestMap.toString());
    String requestHeaders = removeRowJumps(getHeaders(bufferedRequest).toString());
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append(String.format("\n" + Constants.LOGS_REQUEST_METHOD, requestMethod));
    stringBuilder.append(String.format("\n" + Constants.LOGS_REQUEST_URL, requestUri));
    stringBuilder.append(String.format("\n" + Constants.LOGS_REQUEST_PARAMS, requestParams));
    if (LOG_REQUEST_BODY) {
      String requestBody = removeRowJumps(bufferedRequest.getRequestBody());
      stringBuilder.append(String.format("\n" + Constants.LOGS_REQUEST_BODY, requestBody));
    }
    stringBuilder.append(String.format("\n" + Constants.LOGS_REQUEST_HEADERS, requestHeaders));
    return stringBuilder.toString();
  }

  public String logResponse(BufferedResponseWrapper bufferedResponse, long totalRequestTime) {
    int responseStatus = bufferedResponse.getStatus();
    String responseHeaders = removeRowJumps(getHeaders(bufferedResponse).toString());
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append(String.format("\n" + Constants.LOGS_RESPONSE_CODE, responseStatus));
    stringBuilder.append(String.format("\n" + Constants.LOGS_RESPONSE_HEADERS, responseHeaders));
    if (LOG_RESPONSE_BODY) {
      String responseBody = removeRowJumps(bufferedResponse.getContent());
      stringBuilder.append(String.format("\n" + Constants.LOGS_RESPONSE_BODY, responseBody));
    }
    stringBuilder.append(String.format("\n" + Constants.LOGS_TOTAL_REQUEST_TIME, totalRequestTime));
    return stringBuilder.toString();
  }

  static String removeRowJumps(String string) {
    if (StringUtils.isBlank(string)) return null;
    return string.replace("\n", "").replace("\r", "");
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
