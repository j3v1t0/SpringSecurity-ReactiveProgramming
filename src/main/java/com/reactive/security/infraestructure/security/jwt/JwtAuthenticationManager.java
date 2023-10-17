package com.reactive.security.infraestructure.security.jwt;

import com.reactive.security.domain.security.JwtProvider;

import com.reactive.security.infraestructure.exceptions.CustomException;
import com.reactive.security.infraestructure.exceptions.GlobalExceptionHandlers;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Primary
@Component
@Slf4j
@RequiredArgsConstructor
public class JwtAuthenticationManager implements ReactiveAuthenticationManager {
    private final JwtProvider jwtProvider;

    @Override
    public Mono<Authentication> authenticate(Authentication authentication) {
        return Mono.just(authentication)
                .map(auth -> jwtProvider.extractAllClaims(auth.getCredentials().toString()))
                //Throwable.class; clase del error que se "arrojo", no el que queres lanzar (Al ser Throwable es cualquiera excepcion)
                //ex -> new ResponseStatusException(); "ex" es la excepcion que recibio
                .onErrorMap(Throwable.class, ex -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Bad Token"))
                .map(claims -> new UsernamePasswordAuthenticationToken(
                        claims.getSubject(),
                        null,
                        Stream.of(claims.get("roles"))
                                .map(role -> (List<Map<String, String>>) role)
                                .flatMap(role -> role.stream()
                                        .map(r -> r.get("authority"))
                                        .map(SimpleGrantedAuthority::new))
                                .collect(Collectors.toList())
                ));
    }
}
