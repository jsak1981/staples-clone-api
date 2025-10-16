package com.ecommerce.staples_clone.config;

import com.ecommerce.staples_clone.model.ApiAuditLog;
import com.ecommerce.staples_clone.service.AuditService;
import java.time.OffsetDateTime;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class AuditInterceptor implements HandlerInterceptor {

  private static final Logger log = LoggerFactory.getLogger(AuditInterceptor.class);
  private final AuditService auditService;

  @Autowired
  public AuditInterceptor(AuditService auditService) {
    this.auditService = auditService;
  }

  private final ThreadLocal<Long> startTime = new ThreadLocal<>();
  private final ThreadLocal<ApiAuditLog> auditLog = new ThreadLocal<>();

  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
      throws Exception {
    long start = System.currentTimeMillis();
    startTime.set(start);

    ApiAuditLog logEntry = new ApiAuditLog();
    logEntry.setTimestamp((OffsetDateTime.now()));
    logEntry.setRequestId(MDC.get("requestId"));
    logEntry.setCorrelationId((MDC.get("correlationId")));
    logEntry.setHttpMethod(request.getMethod());
    logEntry.setRequestUri(request.getRequestURI());
    logEntry.setSourceIp(request.getRemoteAddr());

    auditLog.set(logEntry);
    return true;
  }

  /*@Override
  public void postHandle(
      HttpServletRequest request,
      HttpServletResponse response,
      Object handler,
      ModelAndView modelAndView)
      throws Exception {
    HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
  }*/

  @Override
  public void afterCompletion(
      HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
      throws Exception {
    ApiAuditLog logEntry = auditLog.get();
    if (logEntry != null) {
      long duration = System.currentTimeMillis() - startTime.get();
      logEntry.setDurationMS(duration);
      logEntry.setResponseStatus(response.getStatus());

      if (ex != null) {
        logEntry.setErrorDetails(ex.getMessage());
      }

      auditService.log(logEntry);

      startTime.remove();
      auditLog.remove();
    }
  }
}
