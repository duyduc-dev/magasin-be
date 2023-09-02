package com.ms.magasinbe.common.exceptions;

import com.ms.magasinbe.common.utils.ResponseUtil;
import com.ms.magasinbe.common.utils.RestAPIResponse;
import com.ms.magasinbe.common.utils.RestAPIStatus;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.List;

/**
 * @author Dang Duy Duc
 */
@ControllerAdvice
@Slf4j
public class ValidationExceptionHandler extends ResponseEntityExceptionHandler {

  @Autowired
  private ResponseUtil responseUtil;

  @ExceptionHandler(value = ApplicationException.class)
  public ResponseEntity<RestAPIResponse<Object>> handleApplicationException(ApplicationException ex, WebRequest request) {

    ResponseEntity<RestAPIResponse<Object>> response;
    if (ex.getApiStatus() == RestAPIStatus.BAD_REQUEST) {
      // handle bad request
      response = responseUtil.buildResponse(RestAPIStatus.BAD_REQUEST, ex.getMessage(), HttpStatus.MULTI_STATUS);
    } else {
      response = responseUtil.buildResponse(ex.getApiStatus(), ex.getMessage(), HttpStatus.OK);
    }

    return response;
  }

  /**
   * @param ex
   * @param headers
   * @param status
   * @param request
   * @return
   */
  @Override
  protected ResponseEntity<Object> handleMissingServletRequestParameter(MissingServletRequestParameterException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
    return new ResponseEntity<Object>(new RestAPIResponse(RestAPIStatus.BAD_REQUEST, ex.getMessage()), headers, status);
  }

  @ExceptionHandler(value = Exception.class)
  public ResponseEntity<RestAPIResponse<Object>> handleUncatchException(Exception ex, WebRequest request) {
    return responseUtil.buildResponse(RestAPIStatus.INTERNAL_SERVER_ERROR, "Please contact System SysAdmin to resolve problem", HttpStatus.INTERNAL_SERVER_ERROR);
  }

  /**
   * @param ex
   * @param headers
   * @param status
   * @param request
   * @return
   */
  @Override
  protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
    return new ResponseEntity<Object>(new RestAPIResponse<>(RestAPIStatus.BAD_REQUEST, null), headers, status);
  }

  /**
   * @param ex
   * @param headers
   * @param status
   * @param request
   * @return
   */
  @Override
  protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
    final String FIELD_NAME_PATTERN = "\\{fieldName}";
    StringBuilder sb = new StringBuilder();
    List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();
    for(FieldError fieldError: fieldErrors){
      String message = "";
      if(fieldError.getDefaultMessage() != null){
        message = fieldError.getDefaultMessage();
        message = message.replaceFirst(FIELD_NAME_PATTERN, fieldError.getField());
      }

      sb.append(message).append("; ");
    }

    return new ResponseEntity<Object>(new RestAPIResponse(RestAPIStatus.BAD_REQUEST, sb.toString()), headers, status);
  }
}
