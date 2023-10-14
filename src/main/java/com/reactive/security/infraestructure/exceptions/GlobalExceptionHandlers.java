
package com.reactive.security.infraestructure.exceptions;

import com.reactive.security.domain.models.dtos.ExceptionDTO;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebExceptionHandler;
import reactor.core.publisher.Mono;

@Configuration
public class GlobalExceptionHandlers {

    //Asi lo podes manejar con Spring Web Flux
    @Bean
    public WebExceptionHandler handler(){
        return (ServerWebExchange exchange, Throwable ex)->{
            //Aca en con el "instance of" devuelve true si la excepcion es del mismo tipo que la clase que pongas
            if(ex instanceof IllegalArgumentException){
                //Logica
                exchange.getResponse().setStatusCode(HttpStatus.BAD_REQUEST);

                //Donde ?
                return exchange.getResponse().setComplete();
            }

            if(ex instanceof CustomException){
                exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                return exchange.getResponse().setComplete();
            }

            //Por si no encuentra algo en los if
            return Mono.error(ex);
        };
    }

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
