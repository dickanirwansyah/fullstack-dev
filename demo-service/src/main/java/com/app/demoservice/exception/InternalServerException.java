package com.app.demoservice.exception;

import lombok.Data;

@Data
public class InternalServerException extends RuntimeException{

    private Integer status;

    public InternalServerException(String message, Integer status){
        super(message);
        this.status = status;
    }
}
