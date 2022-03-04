package com.diegotobalina.framework.core.util;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

public interface ResourcePathBuilder {

  static String build(String suffix) {
    var requestAttributes = RequestContextHolder.currentRequestAttributes();
    var request = ((ServletRequestAttributes) requestAttributes).getRequest();
    String scheme = request.getScheme();
    String serverName = request.getServerName();
    int serverPort = request.getServerPort();
    var prefix = String.format("%s://%s:%s/api/v0", scheme, serverName, serverPort);
    return String.format("%s%s", prefix, suffix);
  }
}
