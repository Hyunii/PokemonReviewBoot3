package com.pokemonreview.api.exceptions.advice;

import com.pokemonreview.api.exceptions.ErrorObject;
import com.pokemonreview.api.exceptions.MyResourceException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    /*
    Spring 6 버전에 추가된 ProblemDetail 객체 에러정보를 담아서 리턴하는 방법
     */
//    @ExceptionHandler(MyResourceException.class)
//    protected ProblemDetail handleException(MyResourceException ex) {
//        ProblemDetail problemDetail = ProblemDetail.forStatus(ex.getStatusCode());
//        problemDetail.seTitle("Not Found");
//        problemDetail.setDetail(ex.getMessage());
//        problemDetail.setProperty("errorCategory", "Generic");
//        problemDetail.setProperty("timestamp", Instant.now());
//        return problemDetail;
//    }

    @ExceptionHandler(MyResourceException.class)
    public ResponseEntity<ErrorObject> handleResourceNotFoundException(MyResourceException ex) {
        ErrorObject errorObject = new ErrorObject();
        errorObject.setStatusCode(ex.getStatusCode());
        errorObject.setMessage(ex.getMessage());

        log.error(ex.getMessage(), ex);

        return new ResponseEntity<ErrorObject>(errorObject, HttpStatusCode.valueOf(ex.getStatusCode()));
    }

    @ExceptionHandler(RuntimeException.class)
    protected ResponseEntity<ErrorObject> handleException(RuntimeException e) {
        ErrorObject errorObject = new ErrorObject();
        errorObject.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
        errorObject.setMessage(e.getMessage());

        log.error(e.getMessage(), e);

        return new ResponseEntity<ErrorObject>(errorObject, HttpStatusCode.valueOf(500));
    }

}