package com.ecommerce.staples_clone.model;

import java.time.OffsetDateTime;
import javax.persistence.*;

@Entity
@Table(name = "api_audit_log")
public class ApiAuditLog {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "timestamp", nullable = false)
  private OffsetDateTime timestamp;

  @Column(name = "request_id", nullable = false, length = 36)
  private String requestId;

  @Column(name = "correlation_id", nullable = false, length = 36)
  private String correlationId;

  @Column(name = "http_method", nullable = false, length = 10)
  private String httpMethod;

  @Column(name = "request_uri", nullable = false)
  private String requestUri;

  @Column(name = "user_id")
  private String userId;

  @Column(name = "source_ip", length = 45)
  private String sourceIp;

  @Column(name = "response_status", nullable = false)
  private Integer responseStatus;

  @Column(name = "duration_ms", nullable = false)
  private Long durationMS;

  @Lob
  @Column(name = "request_payload", columnDefinition = "TEXT")
  private String requestPayload;

  @Lob
  @Column(name = "response_payload", columnDefinition = "TEXT")
  private String responsePayload;

  @Lob
  @Column(name = "error_details", columnDefinition = "TEXT")
  private String errorDetails;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public OffsetDateTime getTimestamp() {
    return timestamp;
  }

  public void setTimestamp(OffsetDateTime timestamp) {
    this.timestamp = timestamp;
  }

  public String getRequestId() {
    return requestId;
  }

  public void setRequestId(String requestId) {
    this.requestId = requestId;
  }

  public String getCorrelationId() {
    return correlationId;
  }

  public void setCorrelationId(String correlationId) {
    this.correlationId = correlationId;
  }

  public String getHttpMethod() {
    return httpMethod;
  }

  public void setHttpMethod(String httpMethod) {
    this.httpMethod = httpMethod;
  }

  public String getRequestUri() {
    return requestUri;
  }

  public void setRequestUri(String requestUri) {
    this.requestUri = requestUri;
  }

  public String getUserId() {
    return userId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }

  public String getSourceIp() {
    return sourceIp;
  }

  public void setSourceIp(String sourceIp) {
    this.sourceIp = sourceIp;
  }

  public Integer getResponseStatus() {
    return responseStatus;
  }

  public void setResponseStatus(Integer responseStatus) {
    this.responseStatus = responseStatus;
  }

  public Long getDurationMS() {
    return durationMS;
  }

  public void setDurationMS(Long durationMS) {
    this.durationMS = durationMS;
  }

  public String getRequestPayload() {
    return requestPayload;
  }

  public void setRequestPayload(String requestPayload) {
    this.requestPayload = requestPayload;
  }

  public String getResponsePayload() {
    return responsePayload;
  }

  public void setResponsePayload(String responsePayload) {
    this.responsePayload = responsePayload;
  }

  public String getErrorDetails() {
    return errorDetails;
  }

  public void setErrorDetails(String errorDetails) {
    this.errorDetails = errorDetails;
  }
}
