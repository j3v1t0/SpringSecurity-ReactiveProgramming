package com.reactive.security.domain.usecase;

import com.reactive.security.domain.models.dtos.LoginDto;
import com.reactive.security.domain.models.dtos.TokenDto;
import reactor.core.publisher.Mono;

public interface LoginUseCase {

    Mono<TokenDto> login(LoginDto loginDto);
}
