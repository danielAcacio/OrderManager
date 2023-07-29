package pt.com.sibs.order.manager.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import pt.com.sibs.order.manager.core.exceptions.DataIntegrityException;
import pt.com.sibs.order.manager.core.exceptions.EntityNotFoundException;
import pt.com.sibs.order.manager.core.exceptions.NegocialException;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

@ControllerAdvice
public class ExceptionHandlerController extends ResponseEntityExceptionHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<Map<String, Object>> entityNotFoundHandler(EntityNotFoundException ex, WebRequest wr){
        Map<String, Object> responseData = new LinkedHashMap<>();
        responseData.put("message", ex.getMessage());
        responseData.put("timestamp", LocalDateTime.now());
        responseData.put("status", HttpStatus.NOT_FOUND.value());
        responseData.put("error", HttpStatus.NOT_FOUND.getReasonPhrase());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseData);
    }


    @ExceptionHandler(NegocialException.class)
    public ResponseEntity<Map<String, Object>> negocialExceptionsHandler(NegocialException ex, WebRequest wr){
        Map<String, Object> responseData = new LinkedHashMap<>();
        responseData.put("message", ex.getMessage());
        responseData.put("timestamp", LocalDateTime.now());
        responseData.put("status", HttpStatus.CONFLICT.value());
        responseData.put("error", HttpStatus.CONFLICT.getReasonPhrase());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(responseData);

    }


    @ExceptionHandler(DataIntegrityException.class)
    public ResponseEntity<Map<String, Object>> dataIntegrityExceptionsHandler(DataIntegrityException ex, WebRequest wr){
        Map<String, Object> responseData = new LinkedHashMap<>();
        responseData.put("message", ex.getMessage());
        responseData.put("timestamp", LocalDateTime.now());
        responseData.put("status", HttpStatus.CONFLICT.value());
        responseData.put("error", HttpStatus.CONFLICT.getReasonPhrase());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(responseData);

    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers,
                                                                  HttpStatus status,
                                                                  WebRequest request) {
        Map<String, Object> responseData = new LinkedHashMap<>();
        responseData.put("message", StringUtils.capitalize(ex.getFieldError().getField())+": "+ex.getFieldError().getDefaultMessage());
        responseData.put("timestamp", LocalDateTime.now());
        responseData.put("status", HttpStatus.BAD_REQUEST.value());
        responseData.put("error", HttpStatus.BAD_REQUEST.getReasonPhrase());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseData);
    }

}
