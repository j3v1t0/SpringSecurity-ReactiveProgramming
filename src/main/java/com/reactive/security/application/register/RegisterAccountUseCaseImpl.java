package com.reactive.security.application.register;

import com.reactive.security.domain.enums.Role;
import com.reactive.security.domain.models.dtos.RegisterAccountDto;
import com.reactive.security.domain.models.entities.AccountClass;
import com.reactive.security.domain.ports.AccountRepository;
import com.reactive.security.domain.usecase.RegisterAccountUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class RegisterAccountUseCaseImpl implements RegisterAccountUseCase {
    private final AccountRepository accountRepository;

    public Mono<AccountClass> createAccount(RegisterAccountDto createAccountDto){

        String identification = createAccountDto.getIdentification();
        String password = createAccountDto.getPassword();
        String fullname = createAccountDto.getFullname();

        var saveAccount = accountRepository.createAccount(identification,password, fullname, Role.ROLE_ADMIN);

        return saveAccount;
    }
}
