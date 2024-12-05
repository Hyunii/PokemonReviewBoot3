package com.pokemonreview.api.exceptions.advice;

import com.pokemonreview.api.exceptions.ErrorObject;
import com.pokemonreview.api.exceptions.MyResourceException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.nio.file.AccessDeniedException;
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

    // 403(권한없음) 예외 발생
    @ExceptionHandler(value = AccessDeniedException.class)
    public void accessDeniedExceptionHandler(Exception e)  {
        try {
            throw new AccessDeniedException(e.getMessage());
        } catch (AccessDeniedException ex) {
            throw new RuntimeException(ex);
        }
    }
    // 401(인증실패) 예외발생
    @ExceptionHandler(value = BadCredentialsException.class)
    public void badCredentialExceptionHandler(BadCredentialsException e){
        throw new BadCredentialsException(e.getMessage());
    }

}