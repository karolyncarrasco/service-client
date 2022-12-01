package com.bootcamp.client.repository;

import com.bootcamp.client.domain.IdentityType;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IdentityTypeRepository extends ReactiveMongoRepository<IdentityType, String> {
}
