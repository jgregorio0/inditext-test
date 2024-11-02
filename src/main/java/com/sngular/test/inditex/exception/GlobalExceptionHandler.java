package com.sngular.test.inditex.exception;

import com.sngular.test.inditex.service.PriceServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

  private static final Logger logger = LoggerFactory.getLogger(PriceServiceImpl.class);

  @ExceptionHandler(PriceNotFoundException.class)
  public ResponseEntity<String> handleProductNotFoundException(PriceNotFoundException e) {
    logger.error("Product not found", e);
    return new ResponseEntity<>("Product not found", HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(MissingServletRequestParameterException.class)
  public ResponseEntity<String> handleMissingServletRequestParameterException(
          MissingServletRequestParameterException e) {
    logger.error("Missing Request Parameter Exception", e);
    return new ResponseEntity<>("Wrong parameters", HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<String> handleGeneralException(Exception e) {
    logger.error("Unexpected exception", e);
    return new ResponseEntity<>("An unexpected error occurred", HttpStatus.INTERNAL_SERVER_ERROR);
  }
}
