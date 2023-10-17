package com.reactive.security.domain.usecase;

import com.reactive.security.domain.models.dtos.RegisterAccountDto;
import com.reactive.security.domain.models.entities.AccountClass;
import reactor.core.publisher.Mono;

public interface RegisterAccountUseCase {
    Mono<AccountClass> createAccount(RegisterAccountDto createAccountDto);
}
