package ru.tsvlad.wayd_user.restapi.controller.advise;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ru.tsvlad.wayd_user.restapi.controller.advise.exceptions.*;

@ControllerAdvice
public class ErrorsHandler {

    @ExceptionHandler(value = {UnsuccessfulLoginException.class})
    public ResponseEntity<String> handleUnsuccessfulLogin(Exception e) {
        return new ResponseEntity<>(getExceptionName(e), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(value = {EmailAlreadyExistsException.class, UsernameAlreadyExistsException.class})
    public ResponseEntity<String> handleEmailAlreadyExists(Exception e) {
        return new ResponseEntity<>(getExceptionName(e), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(value = {ForbiddenException.class})
    public ResponseEntity<String> handleForbidden(Exception e) {
        return new ResponseEntity<>(getExceptionName(e), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(value = {NotFoundException.class})
    public ResponseEntity<String> handleNotFound(Exception e) {
        return new ResponseEntity<>(getExceptionName(e), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = {InvalidPasswordException.class})
    public ResponseEntity<String> handleInvalidPassword(Exception e) {
        return new ResponseEntity<>(getExceptionName(e), HttpStatus.CONFLICT);
    }

    private String getExceptionName(Exception e) {
        String [] exceptionWithPackages = e.getClass().getName().split("\\.");
        return exceptionWithPackages[exceptionWithPackages.length - 1];
    }
}
