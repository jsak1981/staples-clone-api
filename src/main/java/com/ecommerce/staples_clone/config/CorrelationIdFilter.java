package com.ecommerce.staples_clone.config;

import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;

@Component
public class CorrelationIdFilter extends OncePerRequestFilter {

  private static final String CORRELATION_ID_HEADER = "X-Correlation-ID";
  private static final String REQUEST_ID_HEADER = "X-Request-ID";
  private static final String MDC_CORRELATION_ID_KEY = "correlationId";
  private static final String MDC_REQUEST_ID_KEY = "requestId";

  /**
   * Same contract as for {@code doFilter}, but guaranteed to be just invoked once per request
   * within a single request thread. See {@link #shouldNotFilterAsyncDispatch()} for details.
   *
   * <p>Provides HttpServletRequest and HttpServletResponse arguments instead of the default
   * ServletRequest and ServletResponse ones.
   *
   * @param request
   * @param response
   * @param filterChain
   */
  @Override
  protected void doFilterInternal(
      HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {

    try {
      final String requestId = UUID.randomUUID().toString();
      MDC.put(MDC_REQUEST_ID_KEY, requestId);  // requestId
      response.setHeader(REQUEST_ID_HEADER, requestId); // X-Request-ID

      String correlationId = request.getHeader(CORRELATION_ID_HEADER);
      if (correlationId == null || correlationId.isEmpty()) {
        correlationId = requestId;
      }
      MDC.put(MDC_CORRELATION_ID_KEY, correlationId); // correlationId
      response.setHeader(CORRELATION_ID_HEADER, correlationId); //X-Correlation-ID

      filterChain.doFilter(request, response);

    } finally {
      MDC.remove(MDC_REQUEST_ID_KEY);
      MDC.remove(MDC_CORRELATION_ID_KEY);
    }
  }
}
