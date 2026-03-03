package com.chat.demo.exceptions;

import java.time.LocalDateTime;
import java.util.NoSuchElementException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice
public class ControllerAdvice {

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<ErrorResponse> handler1(NoSuchElementException ex, WebRequest web){
        ErrorResponse e = new ErrorResponse();
        //e.setMessage(ex.getMessage());
        e.setError("NO SUCH ELEMENT EXCEPTION");
        e.setStatus(HttpStatus.NOT_FOUND.value());
        e.setTime(LocalDateTime.now());
        e.setPath(web.getContextPath());
        return new ResponseEntity<>(e, HttpStatus.NOT_FOUND);
    }
    

    @ExceptionHandler({BadCredentialsException.class, UsernameNotFoundException.class})
    public ResponseEntity<ErrorResponse> handler3(RuntimeException ex, WebRequest web){
        ErrorResponse e = new ErrorResponse();
        //e.setMessage(ex.getMessage());
        e.setError("E-mail ou senhas inválidos");
        e.setStatus(HttpStatus.UNAUTHORIZED.value());
        e.setTime(LocalDateTime.now());
        e.setPath(web.getContextPath());
        return new ResponseEntity<>(e, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(CodeException.class)
    public ResponseEntity<ErrorResponse> handler4(RuntimeException ex, WebRequest web){
        ErrorResponse e = new ErrorResponse();
        e.setMessage(ex.getMessage());
        e.setError("Erro de código de recuperação");
        e.setStatus(HttpStatus.BAD_REQUEST.value());
        e.setTime(LocalDateTime.now());
        e.setPath(web.getContextPath());
        return new ResponseEntity<>(e, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGeneralError(Exception ex, WebRequest web) {
        ErrorResponse e = new ErrorResponse();
        e.setMessage(ex.getMessage());
        e.setError("Ocorreu um erro interno no servidor.");
        e.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        e.setTime(LocalDateTime.now());
        e.setPath(web.getContextPath());
        
        return new ResponseEntity<>(e, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}