package com.ecommerce.staples_clone.exception;

import com.ecommerce.staples_clone.dto.ErrorResponseDTO;
import org.slf4j.*;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice()
public class GlobalExceptionHandler {
  private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

  @ExceptionHandler(ResourceNotFoundException.class)
  public ResponseEntity<ErrorResponseDTO> handleResourceNotFoundException(
      ResourceNotFoundException ex, WebRequest request) {
    ErrorResponseDTO responseDto =
        new ErrorResponseDTO(
            HttpStatus.NOT_FOUND.value(),
            HttpStatus.NOT_FOUND.getReasonPhrase(),
            ex.getMessage(),
            request.getDescription(false).replace("uri=", ""));
    log.warn("ResourceNotFoundException: {}", ex.getMessage());
    return new ResponseEntity<>(responseDto, HttpStatus.NOT_FOUND);
  }

  // handleAuthenticationFailedException

  @ExceptionHandler(AuthenticationFailedException.class)
  public ResponseEntity<ErrorResponseDTO> handleAuthenticationFailedException(
      AuthenticationFailedException ex, WebRequest request) {

    ErrorResponseDTO responseDTO =
        new ErrorResponseDTO(
            HttpStatus.UNAUTHORIZED.value(),
            HttpStatus.UNAUTHORIZED.getReasonPhrase(),
            ex.getMessage(),
            request.getDescription(false).replace("uri=", ""));

    log.warn("AuthenticationFailedException: {}", ex.getMessage());
    return new ResponseEntity<>(responseDTO, HttpStatus.UNAUTHORIZED);
  }

  @ExceptionHandler(DataIntegrityViolationException.class)
  public ResponseEntity<ErrorResponseDTO> handleDataIntegrityViolationException(
      DataIntegrityViolationException ex, WebRequest request) {

    ErrorResponseDTO responseDto =
        new ErrorResponseDTO(
            HttpStatus.CONFLICT.value(),
            HttpStatus.CONFLICT.getReasonPhrase(),
            "Database error: Could not execute statement. This may be due to a duplicate entry or a foreign key constraint violation.",
            request.getDescription(false).replace("uri=", ""));
    log.error("DataIntegrityViolationException: {}", ex.getMostSpecificCause().getMessage());
    return new ResponseEntity<>(responseDto, HttpStatus.CONFLICT);
  }

  @ExceptionHandler(IllegalStateException.class)
  public ResponseEntity<ErrorResponseDTO> handleIllegalStateException(
      IllegalStateException ex, WebRequest request) {

    ErrorResponseDTO responseDto =
        new ErrorResponseDTO(
            HttpStatus.BAD_REQUEST.value(),
            HttpStatus.BAD_REQUEST.getReasonPhrase(),
            ex.getMessage(),
            request.getDescription(false).replace("uri=", ""));
    log.warn("IllegalStateException: {}", ex.getMessage());
    return new ResponseEntity<>(responseDto, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<ErrorResponseDTO> handleGlobalException(Exception ex, WebRequest request) {
    ErrorResponseDTO responseDto =
        new ErrorResponseDTO(
            HttpStatus.INTERNAL_SERVER_ERROR.value(),
            HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(),
            "An unexpected internal server error occurred.",
            request.getDescription(false).replace("uri=", ""));
    log.error("Unhandled Exception: {}", ex.getMessage(), ex);
    return new ResponseEntity<>(responseDto, HttpStatus.INTERNAL_SERVER_ERROR);
  }
}
