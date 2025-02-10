package com.javeiros.microserviceB.exception;

import com.javeiros.microserviceB.jacoco.ExcludeFromJacocoGeneratedReport;
import com.mongodb.MongoSocketOpenException;
import com.mongodb.MongoSocketReadException;
import com.mongodb.MongoTimeoutException;
import com.mongodb.MongoWriteException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@ExcludeFromJacocoGeneratedReport
@RequiredArgsConstructor
@RestControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorMessage> entityNotFound(RuntimeException exception, HttpServletRequest request) {
        log.error("Api Error - ", exception);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).contentType(MediaType.APPLICATION_JSON).body(new ErrorMessage(request, HttpStatus.NOT_FOUND, exception.getMessage()));

    }

    @ExceptionHandler(DataBaseException.class)
    public ResponseEntity<ErrorMessage> dataBaseException(RuntimeException exception, HttpServletRequest request) {
        log.error("Api Error - ", exception);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorMessage(request, HttpStatus.BAD_REQUEST, exception.getMessage()));
    }


    @ExceptionHandler({MongoTimeoutException.class, MongoSocketReadException.class, MongoWriteException.class, MongoSocketOpenException.class})
    public ResponseEntity<String> handleMongoTimeout(MongoTimeoutException ex) {
        return ResponseEntity.status(504).body("Erro ao conectar ao banco de dados: ");
    }
}
