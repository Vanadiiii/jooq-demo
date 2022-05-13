package me.imatveev.jooqdemo.web.handler;

import me.imatveev.jooqdemo.domain.exception.CountryNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@ControllerAdvice("me.imatveev.jooqdemo.web")
public class CountryExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(CountryNotFoundException.class)
    public void countryNotFoundHandle(HttpServletResponse response) throws IOException {
        response.sendError(HttpStatus.NOT_FOUND.value());
    }
}
