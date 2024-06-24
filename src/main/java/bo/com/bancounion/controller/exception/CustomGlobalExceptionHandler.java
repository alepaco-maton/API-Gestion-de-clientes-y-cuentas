/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package bo.com.bancounion.controller.exception;

import bo.com.bancounion.controller.exception.AppException;
import bo.com.bancounion.exception.ErrorCode;
import bo.com.bancounion.exception.ExceptionNotFoundResponse;
import bo.com.bancounion.exception.ExceptionResponse;
import bo.com.bancounion.service.MultiLanguageMessagesService;
import java.net.ConnectException;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 *
 * @author alepaco.com
 */
@Log4j2
@Component
@ControllerAdvice
public class CustomGlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @Autowired
    MultiLanguageMessagesService mlms;

    @ExceptionHandler({ExceptionResponse.class})
    public ResponseEntity<Object> mihandleAll(Exception ex, WebRequest request) {
        ExceptionResponse exception = (ExceptionResponse) ex;

        ResponseEntity<Object> out = new ResponseEntity<>(
                new AppException(HttpStatus.UNPROCESSABLE_ENTITY, exception.getCode(),
                        exception.getMessage()),
                HttpHeaders.EMPTY, HttpStatus.UNPROCESSABLE_ENTITY);

        return out;
    }

    @ExceptionHandler({ExceptionNotFoundResponse.class})
    public ResponseEntity<Object> mihandleNotFound(Exception ex, WebRequest request) {
        ExceptionNotFoundResponse exception = (ExceptionNotFoundResponse) ex;

        ResponseEntity<Object> out = new ResponseEntity<>(
                new AppException(HttpStatus.NOT_FOUND, exception.getCode(),
                        exception.getMessage()),
                HttpHeaders.EMPTY, HttpStatus.NOT_FOUND);

        return out;
    }

    @ExceptionHandler({Exception.class, ConnectException.class})
    public ResponseEntity<Object> handleAll(Exception ex, WebRequest request) {
        ResponseEntity<Object> out = new ResponseEntity<>(
                new AppException(HttpStatus.UNPROCESSABLE_ENTITY,
                        ErrorCode.ERROR_PROCESSING_THE_TRANSACTION.getCode(),
                        mlms.getMessage(ErrorCode.ERROR_PROCESSING_THE_TRANSACTION.getCode())),
                HttpHeaders.EMPTY, HttpStatus.UNPROCESSABLE_ENTITY);

        log.error(ex.getCause(), ex);

        StringBuilder sb = new StringBuilder();
        sb.append("-------------------RESPONSE - HTTP STATUS : " + HttpStatus.UNPROCESSABLE_ENTITY + " ----------------------\n").
                append("DATA ").append(out).append(", \n").
                append("------------------------------------------------\n");

        log.info(sb.toString());

        return out;
    }

}
