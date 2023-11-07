package com.app.demoservice.controller;

import com.app.demoservice.exception.DataNotfoundException;
import com.app.demoservice.model.RestResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalHandlerController {

    @ExceptionHandler(DataNotfoundException.class)
    public ResponseEntity<RestResponse> handleNotfoundException(DataNotfoundException e){
        log.info("error data not found =  ",e.fillInStackTrace());
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(RestResponse.isFailed(e.getMessage(), HttpStatus.NOT_FOUND.value()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<RestResponse> handleNotfoundException(Exception e){
        log.info("error backend =  ",e.fillInStackTrace());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(RestResponse.isFailed(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value()));
    }
}
