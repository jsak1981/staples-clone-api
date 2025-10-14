package com.ecommerce.staples_clone.dto;

import java.time.LocalDateTime;

public class ErrorResponseDTO {

  private final LocalDateTime timeStamp;
  private final int status;
  private final String error;
  private final String message;
  private final String path;

  public ErrorResponseDTO(int status, String error, String message, String path) {
    this.timeStamp = LocalDateTime.now();
    this.status = status;
    this.error = error;
    this.message = message;
    this.path = path;
  }

  public LocalDateTime getTimeStamp() {
    return timeStamp;
  }

  public int getStatus() {
    return status;
  }

  public String getError() {
    return error;
  }

  public String getMessage() {
    return message;
  }

  public String getPath() {
    return path;
  }
}
