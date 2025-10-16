package com.ecommerce.staples_clone.service;

import com.ecommerce.staples_clone.model.ApiAuditLog;
import com.ecommerce.staples_clone.repository.ApiAuditLogRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class AuditService {
  private static final Logger log = LoggerFactory.getLogger(AuditService.class);

  private final ApiAuditLogRepository apiAuditLogRepository;

  @Autowired
  public AuditService(ApiAuditLogRepository apiAuditLogRepository) {
    this.apiAuditLogRepository = apiAuditLogRepository;
  }

  @Async
  public void log(ApiAuditLog auditLog) {
    try {
      apiAuditLogRepository.save(auditLog);
    } catch (Exception ex) {
      log.error("Failed to save Audit log.", ex);
    }
  }
}
