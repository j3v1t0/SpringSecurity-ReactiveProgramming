package com.reactive.security.infraestructure.persistence.adapters;

import com.reactive.security.domain.enums.Role;
import com.reactive.security.domain.models.dtos.LoginDto;
import com.reactive.security.domain.models.dtos.TokenDto;
import com.reactive.security.domain.models.entities.AccountClass;
import com.reactive.security.domain.ports.AccountRepository;
import com.reactive.security.domain.security.JwtProvider;
import com.reactive.security.infraestructure.exceptions.CustomException;
import com.reactive.security.infraestructure.persistence.entities.AccountEntity;
import com.reactive.security.infraestructure.persistence.repositories.AccountReactiveRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.util.function.Function;

@Slf4j
@RequiredArgsConstructor
@Repository
public class AccountRepositoryAdapter implements AccountRepository {
    private final AccountReactiveRepository accountRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;

    private final Function<AccountEntity, AccountClass> mapper = accountEntity -> new AccountClass(
            accountEntity.getAccountUuid(),
            accountEntity.getIdentification(),
            accountEntity.getFullname(),
            accountEntity.getPassword(),
            accountEntity.getRoles(),
            accountEntity.getCreatedAt(),
            accountEntity.getLastLoginAt()
    );

    @Override
    public Mono<TokenDto> login(String identification, String password){
        return accountRepository.findByIdentification(identification)
                .filter(user -> passwordEncoder.matches(identification, user.getPassword()))
                .map(user -> new TokenDto(jwtProvider.generateToken(user)))
                .switchIfEmpty(Mono.error(new CustomException(HttpStatus.NOT_FOUND, "Bad Credentials")));
    }

    @Override
    public Mono<AccountClass> createAccount(String identification, String password, String fullname, Role rol) {
        return this.accountRepository.findByIdentification(identification)
                .flatMap(isRegistered ->Mono.error(new CustomException(HttpStatus.BAD_REQUEST, "This account already exists")))
                .switchIfEmpty(accountRepository.save(new AccountEntity(identification, fullname, passwordEncoder.encode(password), rol)))
                .map(entity-> mapper.apply((AccountEntity) entity));
    }
}
