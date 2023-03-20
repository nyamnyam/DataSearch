package com.github.nyamnyam.common.controller;

import com.github.nyamnyam.common.enums.ResponseCode;
import com.github.nyamnyam.common.model.BasicResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.validation.UnexpectedTypeException;
import javax.validation.ValidationException;
import java.security.InvalidParameterException;

@Slf4j
@RestControllerAdvice
public class ExceptionAdvisor {


    @ExceptionHandler({UnexpectedTypeException.class, TypeMismatchException.class, MethodArgumentTypeMismatchException.class})
    private ResponseEntity<BasicResponse> handleTypeMismatchException() {
        return ResponseEntity.badRequest().body(BasicResponse.toResponse(ResponseCode.ERROR_1000, null));
    }

    @ExceptionHandler({InvalidParameterException.class, ValidationException.class, BindException.class})
    protected ResponseEntity<BasicResponse> handleInvalidParameterException() {
        return ResponseEntity.badRequest().body(BasicResponse.toResponse(ResponseCode.ERROR_1000, null));
    }

    @ExceptionHandler({ IllegalArgumentException.class, IllegalStateException.class })
    private ResponseEntity<BasicResponse> handlerTypeIllegalException(InvalidParameterException e) {
        return ResponseEntity.badRequest().body(BasicResponse.toResponse(ResponseCode.ERROR_9001, null));
    }

    @ExceptionHandler(Exception.class)
    private Object handlerException(Exception e) {
        log.error("handlerException:: ",e);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(BasicResponse.toResponse(ResponseCode.ERROR_9999, null));
    }

}
