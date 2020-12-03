package io.github.aveuiller.experiment;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@ControllerAdvice
public class MyApplicationControllerAdvice extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ApiException.class)
    @ResponseBody
    ResponseEntity<Map<String, String>> handleControllerException(HttpServletRequest request, ApiException apiException) {
        return new ResponseEntity<>(apiException.getJsonMessage(), apiException.getStatus());
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return new ResponseEntity<>(ex.getFieldErrors(), status);
    }
}
