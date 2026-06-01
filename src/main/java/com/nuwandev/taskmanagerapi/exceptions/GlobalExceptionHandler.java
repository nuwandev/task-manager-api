package com.nuwandev.taskmanagerapi.exceptions;

import com.nuwandev.taskmanagerapi.dto.response.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(TaskNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleTaskNotFoundException(TaskNotFoundException exception) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ErrorResponse(false, exception.getMessage(), null));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationException(MethodArgumentNotValidException exception) {
        List<ErrorResponse.Error> validationErrors = exception.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(this::mapFieldError)
                .toList();

        return ResponseEntity.badRequest()
                .body(new ErrorResponse(false, "Validation failed", validationErrors.toArray(ErrorResponse.Error[]::new)));
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorResponse> handleTypeMismatchException(MethodArgumentTypeMismatchException exception) {
        String errorMessage = "Invalid value for '" + exception.getName() + "'";

        return ResponseEntity.badRequest()
                .body(new ErrorResponse(false, errorMessage,
                        new ErrorResponse.Error[]{new ErrorResponse.Error(exception.getName(), errorMessage)}));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleUnhandledException() {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ErrorResponse(false, "Something went wrong", null));
    }

    private ErrorResponse.Error mapFieldError(FieldError fieldError) {
        return new ErrorResponse.Error(fieldError.getField(), fieldError.getDefaultMessage());
    }
}

