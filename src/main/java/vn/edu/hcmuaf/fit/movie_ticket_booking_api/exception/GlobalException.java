package vn.edu.hcmuaf.fit.movie_ticket_booking_api.exception;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.handler.response.HttpResponse;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.handler.response.HttpResponseError;

import java.sql.SQLException;

@RestControllerAdvice
public class GlobalException {
    @ExceptionHandler(BindException.class)
    public ResponseEntity<HttpResponse> handleBindException(BindException e) {
        StringBuilder stringBuilder = new StringBuilder();
        for(ObjectError error : e.getAllErrors()) {
            stringBuilder.append(error.getDefaultMessage()).append(", ");
        }
        String errorMessage = stringBuilder.substring(0, stringBuilder.length() - 2);
        return ResponseEntity.ok(HttpResponseError.error(HttpStatus.BAD_REQUEST, errorMessage.toString()).build());
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<HttpResponse> handleNotFoundException(NotFoundException e) {
        return ResponseEntity.badRequest().body(HttpResponseError.error(HttpStatus.NOT_FOUND, e.getMessage()).build());
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<HttpResponse> handleRuntimeException(RuntimeException e) {
        return ResponseEntity.badRequest().body(HttpResponseError.error(HttpStatus.BAD_REQUEST, e.getMessage()).build());
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<HttpResponse> handleDataIntegrityViolationException(DataIntegrityViolationException e) {
        return ResponseEntity.badRequest().body(HttpResponseError.error(HttpStatus.BAD_REQUEST, "Not found data ").build());
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<HttpResponse> handleBadRequestException(BadRequestException e) {
        return ResponseEntity.badRequest().body(HttpResponseError.error(HttpStatus.BAD_REQUEST, e.getMessage()).build());
    }

}
