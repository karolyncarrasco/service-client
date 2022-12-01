package com.bootcamp.client.repository;

import com.bootcamp.client.domain.ClientType;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientTypeRepository extends ReactiveMongoRepository<ClientType, String> {
}
