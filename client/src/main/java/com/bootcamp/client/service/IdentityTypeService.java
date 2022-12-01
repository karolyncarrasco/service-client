package com.bootcamp.client.service;

import com.bootcamp.client.domain.Client;
import com.bootcamp.client.domain.IdentityType;
import com.bootcamp.client.repository.IdentityTypeRepository;
import com.bootcamp.client.web.mapper.IdentityTypeMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDate;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class IdentityTypeService {

    @Autowired
    IdentityTypeRepository identityTypeRepository;

    @Autowired
    private IdentityTypeMapper identityTypeMapper;

    public Flux<IdentityType> findAll(){
        log.debug("findAll executed");
        return identityTypeRepository.findAll();
    }


    public Mono<IdentityType> findById(String id){
        log.debug("findById executed {}", id);
        return identityTypeRepository.findById(id);
    }


    public Mono<IdentityType> create(IdentityType identityType){
        log.debug("create executed {}", identityType);
        identityType.setCreationDate(LocalDate.now());
        identityType.setCreationUser(System.getProperty("user.name"));
        identityType.setStatus(true);
        return identityTypeRepository.save(identityType);
    }


    public Mono<IdentityType> update(String id,  IdentityType identityType){
        log.debug("update executed {}:{}", id, identityType);
        identityType.setModifiedDate(LocalDate.now());
        identityType.setModifiedUser(System.getProperty("user.name"));
        return identityTypeRepository.findById(id)
                .flatMap(dbType -> {
                    identityTypeMapper.update(dbType, identityType);
                    return identityTypeRepository.save(dbType);
                });
    }


    public Mono<IdentityType> delete(String id){
        log.debug("delete executed {}", id);
        return identityTypeRepository.findById(id)
                .flatMap(existingType -> identityTypeRepository.delete(existingType)
                        .then(Mono.just(existingType)));
    }
}
