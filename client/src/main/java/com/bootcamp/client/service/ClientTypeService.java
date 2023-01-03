package com.bootcamp.client.service;

import com.bootcamp.client.domain.ClientType;
import com.bootcamp.client.repository.ClientTypeRepository;
import com.bootcamp.client.web.mapper.ClientTypeMapper;
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
public class ClientTypeService {
    @Autowired
    private ClientTypeRepository clientTypeRepository;

    @Autowired
    private ClientTypeMapper clientTypeMapper;
    public Flux<ClientType> findAll() {
        log.debug("findAll executed");
        return clientTypeRepository.findAll();
    }

    public Mono<ClientType> findById(String id) {
        log.debug("findById executed {}", id);
        return clientTypeRepository.findById(id);
    }


    public Mono<ClientType> create(ClientType clientType) {
        log.debug("create executed {}", clientType);
        clientType.setCreationDate(LocalDate.now());
        clientType.setCreationUser(System.getProperty("user.name"));
        clientType.setStatus(true);
        return clientTypeRepository.save(clientType);
    }


    public Mono<ClientType> update(String id,  ClientType clientType) {
        log.debug("update executed {}:{}", id, clientType);
        clientType.setModifiedDate(LocalDate.now());
        clientType.setModifiedUser(System.getProperty("user.name"));
        return clientTypeRepository.findById(id)
                .flatMap(dbType -> {
                    clientTypeMapper.update(dbType, clientType);
                    return clientTypeRepository.save(dbType);
                });
    }


    public Mono<ClientType> delete(String id) {
        log.debug("delete executed {}", id);
        return clientTypeRepository.findById(id)
                .flatMap(existingType -> clientTypeRepository.delete(existingType)
                        .then(Mono.just(existingType)));
    }
}
