package com.example.chatwebsocketkafkajwtrestapi.exception;


import com.example.chatwebsocketkafkajwtrestapi.dto.ExceptionMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.util.Date;

@RestControllerAdvice
public class NotFoundExceptionController {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<Object> notfound(NotFoundException exception) {
        ExceptionMessage exceptionMessage = ExceptionMessage.builder()
                .date(new Date())
                .message(exception.getMessage())
                .build();
        return new ResponseEntity<>(exceptionMessage, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> notfoundexception(Exception exception){
        ExceptionMessage exceptionMessage = ExceptionMessage.builder()
                .date(new Date())
                .message(exception.getMessage())
                .build();
        return new ResponseEntity<>(exceptionMessage, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
