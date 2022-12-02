package com.bootcamp.client.web;

import com.bootcamp.client.domain.Client;
import com.bootcamp.client.domain.IdentityType;
import com.bootcamp.client.service.ClientService;
import com.bootcamp.client.web.mapper.ClientMapper;
import com.bootcamp.client.web.mapper.IdentityTypeMapper;
import com.bootcamp.client.web.model.ClientModel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import java.net.URI;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/client")

public class ClientController {

    @Value("${spring.application.name}")
    String name;

    @Value("${server.port}")
    String port;

    @Autowired
    private ClientService clientService;


    @Autowired
    private ClientMapper clientMapper;
    @Autowired
    private IdentityTypeMapper identityTypeMapper;


    @GetMapping()
    public Mono<ResponseEntity<Flux<ClientModel>>> getAll(){
        log.info("getAll executed");
        return Mono.just(ResponseEntity.ok()
                .body(clientService.findAll()
                        .map(Client -> clientMapper.entityToModel(Client))));
    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<ClientModel>> getById(@PathVariable String id){
        log.info("getById executed {}", id);
        Mono<Client> response = clientService.findById(id);
        return response
                .map(Client -> clientMapper.entityToModel(Client))
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Mono<ResponseEntity<ClientModel>> create(@Valid @RequestBody ClientModel request){
        log.info("create executed {}", request);
        return clientService.create(clientMapper.modelToEntity(request))
                .map(client -> clientMapper.entityToModel((Client) client))
                .flatMap(c -> Mono.just(ResponseEntity.created(URI.create(String.format("http://%s:%s/%s/%s", name, port, "Client", c.getName())))
                        .body(c)));
                //.defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public Mono<ResponseEntity<ClientModel>> updateById(@PathVariable String id, @Valid @RequestBody ClientModel request){
        log.info("updateById executed {}:{}", id, request);
        return clientService.update(id, clientMapper.modelToEntity(request))
                .map(Client -> clientMapper.entityToModel(Client))
                .flatMap(c -> Mono.just(ResponseEntity.created(URI.create(String.format("http://%s:%s/%s/%s", name, port, "Client", c.getId())))
                        .body(c)))
                .defaultIfEmpty(ResponseEntity.badRequest().build());
    }

    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Void>> deleteById(@PathVariable String id){
        log.info("deleteById executed {}", id);
        return clientService.delete(id)
                .map( r -> ResponseEntity.ok().<Void>build())
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @GetMapping("/findByIdentityNumber/{id}")
    public Mono<ResponseEntity<ClientModel>> findByIdentityNumber(@PathVariable(value = "id") String identityNumber){
        log.info("getById executed {}", identityNumber);
        Mono<Client> response = clientService.findByIdentityNumber(identityNumber);
        return response
                .map(Client -> clientMapper.entityToModel(Client))
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

}
