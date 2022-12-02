package com.bootcamp.client.service;

import com.bootcamp.client.common.ErrorMessage;
import com.bootcamp.client.common.FunctionalException;
import com.bootcamp.client.domain.Client;
import com.bootcamp.client.repository.ClientRepository;
import com.bootcamp.client.repository.ClientTypeRepository;
import com.bootcamp.client.repository.IdentityTypeRepository;
import com.bootcamp.client.web.mapper.ClientMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional

public class ClientService {

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private ClientTypeRepository clientTypeRepository;

    @Autowired
    private IdentityTypeRepository identityTypeRepository;

    @Autowired
    private ClientMapper clientMapper;


    public Flux<Client> findAll(){
        log.debug("findAll executed");
        return clientRepository.findAll();
    }


    public Mono<Client> findById(String ClientId){
        log.debug("findById executed {}", ClientId);
        return clientRepository.findById(ClientId);
    }


    public Mono<Object> create(Client client){
        log.debug("create executed {}", client);
        return clientRepository.findByIdentityNumber(client.getIdentityNumber())
                .flatMap(x-> Mono.error(new FunctionalException(ErrorMessage.CLIENT_DUPLICATE.getValue())))
                .switchIfEmpty(
                        clientTypeRepository.findById(client.getClientType().getId())
                                .flatMap(x -> {
                                    client.setClientType(x);
                                    return identityTypeRepository.findById(client.getIdentityType().getId())
                                            .flatMap(y -> {
                                                client.setIdentityType(y);
                                                log.debug("create executed {}", client);
                                                return clientRepository.save(client);
                                            })
                                            .switchIfEmpty(Mono.error(new FunctionalException(ErrorMessage.IDENTITYTYPE_NOT_FOUNT.getValue())));
                                })
                                .switchIfEmpty(Mono.error(new FunctionalException(ErrorMessage.CLIENTTYPE_NOT_FOUND.getValue())))
                );
    }


    public Mono<Client> update(String clientId,  Client client){
        log.debug("update executed {}:{}", clientId, client);
            return clientRepository.findById(clientId)
                    .flatMap(dbClient -> {
                        clientMapper.update(dbClient, client);
                        return clientRepository.save(dbClient);
                    });
    }


    public Mono<Client> delete(String ClientId){
        log.debug("delete executed {}", ClientId);
        return clientRepository.findById(ClientId)
                .flatMap(existingClient -> clientRepository.delete(existingClient)
                        .then(Mono.just(existingClient)));
    }

    public Mono<Client> findByIdentityNumber(String identityNumber){
        log.debug("findByIdentityNumber executed {}", identityNumber);
        return clientRepository.findByIdentityNumber(identityNumber)
                .switchIfEmpty(Mono.error(new FunctionalException(ErrorMessage.CLIENT_NOT_FOUND.getValue())));
    }
}
