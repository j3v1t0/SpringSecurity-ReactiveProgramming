package com.reactive.security.infraestructure.exceptions;

import com.reactive.security.domain.models.dtos.ExceptionDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

public class GlobalExceptionHandlers {
    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<ExceptionDTO> handlerBadRequest(Exception exception){
        var exceptionDto = ExceptionDTO.builder()
                .detail(exception.getMessage())
                .status(HttpStatus.BAD_REQUEST.value())
                .type(exception.getClass().getTypeName())
                .build();

        return ResponseEntity.badRequest().body(exceptionDto);
    }
    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<ExceptionDTO> handlerUnauthorized(Exception exception){
        var exceptionDto = ExceptionDTO.builder()
                .detail(exception.getMessage())
                .status(HttpStatus.UNAUTHORIZED.value())
                .type(exception.getClass().getTypeName())
                .build();

        return ResponseEntity.badRequest().body(exceptionDto);
    }
}
