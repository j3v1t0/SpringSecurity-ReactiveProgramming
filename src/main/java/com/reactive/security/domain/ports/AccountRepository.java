package com.reactive.security.domain.ports;

import com.reactive.security.domain.enums.Role;
import com.reactive.security.domain.models.dtos.LoginDto;
import com.reactive.security.domain.models.dtos.TokenDto;
import com.reactive.security.domain.models.entities.AccountClass;
import reactor.core.publisher.Mono;

public interface AccountRepository {
    Mono<TokenDto> login(String identification, String password);

    Mono<AccountClass> createAccount(final String email, final String password, String fullname, final Role rol);
}
