package com.reactive.security.infraestructure.security.authentication;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ReactiveAuthenticationService {

    private final ReactiveAuthenticationManager manager;
}
