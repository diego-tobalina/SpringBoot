package com.diegotobalina.framework.core.logs.wrappers;

import com.diegotobalina.framework.core.logs.streams.BufferedServletInputStream;

import javax.servlet.ServletInputStream;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.*;

/** @author diegotobalina created on 24/06/2020 */
public class BufferedRequestWrapper extends HttpServletRequestWrapper implements ServletRequest {

  private byte[] buffer = null;

  public BufferedRequestWrapper(HttpServletRequest req) throws IOException {
    super(req);
    // Read InputStream and store its content in a buffer.
    InputStream is = req.getInputStream();
    var baos = new ByteArrayOutputStream();
    var buf = new byte[1024];
    int read;
    while ((read = is.read(buf)) > 0) {
      baos.write(buf, 0, read);
    }
    this.buffer = baos.toByteArray();
  }

  @Override
  public ServletInputStream getInputStream() {
    var bais = new ByteArrayInputStream(this.buffer);
    return new BufferedServletInputStream(bais);
  }

  public String getRequestBody() throws IOException {
    try (var reader = new BufferedReader(new InputStreamReader(this.getInputStream()))) {
      String line = null;
      var inputBuffer = new StringBuilder();
      do {
        line = reader.readLine();
        if (null != line) {
          inputBuffer.append(line.trim());
        }
      } while (line != null);
      return inputBuffer.toString().trim();
    }
  }
}
