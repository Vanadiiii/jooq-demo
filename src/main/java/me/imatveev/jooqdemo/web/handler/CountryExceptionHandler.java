package me.imatveev.jooqdemo.web.handler;

import me.imatveev.jooqdemo.domain.exception.CountryNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice("me.imatveev.jooqdemo.web")
public class CountryExceptionHandler {
    @ExceptionHandler
    public ResponseEntity<String> handle(CountryNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(e.getMessage());
    }
}
