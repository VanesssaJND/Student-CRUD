package com.example.demo.student.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class badRequestException extends RuntimeException{

    public badRequestException(String msg) {
        super(msg);
    }
}