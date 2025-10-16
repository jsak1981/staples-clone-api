package com.ecommerce.staples_clone.repository;

import com.ecommerce.staples_clone.model.ApiAuditLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ApiAuditLogRepository extends JpaRepository<ApiAuditLog, Long> {}
