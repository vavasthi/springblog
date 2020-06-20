package in.springframework.blog.tutorials.utils;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

public class HTTPUtils {
  public static Optional<String> getOptionalParameter(HttpServletRequest httpRequest, String parameterName) {
    String[] values = httpRequest.getParameterValues(parameterName);
    if (values == null || values.length == 0) {
      return Optional.empty();
    }
    return Optional.of(values[0]);
  }
  public static Optional<String> getOptionalParameterOrHeader(HttpServletRequest httpRequest, String headerName) {
    Optional<String> optionalParameter = getOptionalParameter(httpRequest, headerName);
    if (optionalParameter.isPresent()) {
      return optionalParameter;
    }
    String header = httpRequest.getHeader(headerName);
    if (header == null) {
      return Optional.empty();
    }
    else {
      return Optional.of(header);
    }
  }
}
