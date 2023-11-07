package com.app.demoservice.exception;

import lombok.Data;

@Data
public class DataNotfoundException extends RuntimeException{

    private Integer status;

    public DataNotfoundException(String message, Integer status){
        super(message);
        this.status = status;
    }
}
