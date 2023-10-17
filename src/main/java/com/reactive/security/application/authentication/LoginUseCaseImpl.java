package com.reactive.security.application.authentication;

import com.reactive.security.domain.models.dtos.LoginDto;
import com.reactive.security.domain.models.dtos.TokenDto;
import com.reactive.security.domain.ports.AccountRepository;
import com.reactive.security.domain.usecase.LoginUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class LoginUseCaseImpl implements LoginUseCase {

    private final AccountRepository accountRepository;

    public Mono<TokenDto> login(LoginDto loginDto) {
        String identification = loginDto.getIdentification();
        String password = loginDto.getPassword();

        var login = accountRepository.login(identification,password);

        return login;
    }
}
