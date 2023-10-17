package com.reactive.security.infraestructure.config;

import com.reactive.security.infraestructure.persistence.repositories.SecurityContextRepository;
import com.reactive.security.infraestructure.security.jwt.JwtAuthorizationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@EnableWebSecurity
@EnableReactiveMethodSecurity
@RequiredArgsConstructor
public class MainSecurity {

    private final SecurityContextRepository securityContextRepository;
    @Bean
    public SecurityWebFilterChain filterChain(ServerHttpSecurity httpSecurity, JwtAuthorizationFilter jwtAuthorizationFilter){
        return httpSecurity
                .authorizeExchange((authorizeExchange) ->
                        authorizeExchange.pathMatchers("/auth/**"))
                .csrf((csrf) -> csrf.disable())
                .addFilterAfter(jwtAuthorizationFilter, SecurityWebFiltersOrder.FIRST)
                .securityContextRepository(securityContextRepository)
                .formLogin((formLogin) -> formLogin.disable()
                .logout((logout) -> logout.disable())
                .httpBasic((httpBasic) -> httpBasic.disable()))
                .build();
    }
}
