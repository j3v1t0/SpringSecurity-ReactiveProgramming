package com.reactive.security.infraestructure.persistence.repositories;

import com.reactive.security.infraestructure.persistence.entities.AccountEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

public interface AccountReactiveRepository extends ReactiveCrudRepository<AccountEntity, String> {
    Mono<AccountEntity> findByIdentification(String identification);
}
