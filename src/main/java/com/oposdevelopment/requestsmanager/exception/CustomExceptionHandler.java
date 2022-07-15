package com.oposdevelopment.requestsmanager.exception;

import com.oposdevelopment.requestsmanager.exception.dto.ApplicationExceptionRS;
import com.oposdevelopment.requestsmanager.exception.mapper.ApplicationExceptionMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
@RestController
@RequiredArgsConstructor
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

    private final ApplicationExceptionMapper exceptionMapper;

    @ExceptionHandler(ApplicationException.class)
    public ResponseEntity<ApplicationExceptionRS> handleApplicationException(ApplicationException applicationException) {
        return new ResponseEntity<>(exceptionMapper.map(applicationException), HttpStatus.BAD_REQUEST);
    }

    @Override
    protected ResponseEntity handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                          HttpHeaders headers, HttpStatus status, WebRequest request) {
        List<MethodArgumentExceptionDetails> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(fieldError -> new MethodArgumentExceptionDetails(fieldError.getField(), fieldError.getDefaultMessage()))
                .collect(Collectors.toList());

        return new ResponseEntity<>(errors, headers, status);
    }
}
