package com.example.onlineresto.utils.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class OutOfStockException extends RuntimeException{
    public  OutOfStockException(String message) {
        super(message);
    }
}
