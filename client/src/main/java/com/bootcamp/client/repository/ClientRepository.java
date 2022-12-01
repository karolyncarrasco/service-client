package com.bootcamp.client.repository;

import com.bootcamp.client.domain.Client;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface ClientRepository extends ReactiveMongoRepository<Client, String> {
    public Mono<Client> findByIdentityNumber(String identityNumber);
}
