package ru.tsvlad.wayd_user.restapi.controller.advise;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ru.tsvlad.wayd_user.restapi.controller.advise.exceptions.UnsuccessfulLoginException;

@ControllerAdvice
public class ErrorsHandler {

    @ExceptionHandler(value = {UnsuccessfulLoginException.class})
    protected ResponseEntity<String> handleUnsuccessfulLogin() {
        return new ResponseEntity<>("", HttpStatus.UNAUTHORIZED);
    }
}
