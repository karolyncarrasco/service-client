package com.bootcamp.client.web;

import com.bootcamp.client.domain.ClientType;
import com.bootcamp.client.service.ClientTypeService;
import com.bootcamp.client.web.mapper.ClientTypeMapper;
import com.bootcamp.client.web.model.ClientTypeModel;
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
@RequestMapping("/v1/clientType")
public class ClientTypeController {
    @Value("${spring.application.name}")
    String name;

    @Value("${server.port}")
    String port;

    @Autowired
    private ClientTypeService clientTypeService;


    @Autowired
    private ClientTypeMapper clientTypeMapper;


    @GetMapping()
    public Mono<ResponseEntity<Flux<ClientTypeModel>>> getAll(){
        log.info("getAll executed");
        return Mono.just(ResponseEntity.ok()
                .body(clientTypeService.findAll()
                        .map(Type -> clientTypeMapper.entityToModel(Type))));
    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<ClientTypeModel>> getById(@PathVariable String id){
        log.info("getById executed {}", id);
        Mono<ClientType> response = clientTypeService.findById(id);
        return response
                .map(Type -> clientTypeMapper.entityToModel(Type))
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Mono<ResponseEntity<ClientTypeModel>> create(@Valid @RequestBody ClientTypeModel request){
        log.info("create executed {}", request);
        return clientTypeService.create(clientTypeMapper.modelToEntity(request))
                .map(Type -> clientTypeMapper.entityToModel(Type))
                .flatMap(c -> Mono.just(ResponseEntity.created(URI.create(String.format("http://%s:%s/%s/%s", name, port, "ClientType", c.getId())))
                        .body(c)))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public Mono<ResponseEntity<ClientTypeModel>> updateById(@PathVariable String id, @Valid @RequestBody ClientTypeModel request){
        log.info("updateById executed {}:{}", id, request);
        return clientTypeService.update(id, clientTypeMapper.modelToEntity(request))
                .map(Type -> clientTypeMapper.entityToModel(Type))
                .flatMap(c -> Mono.just(ResponseEntity.created(URI.create(String.format("http://%s:%s/%s/%s", name, port, "ClientType", c.getId())))
                        .body(c)))
                .defaultIfEmpty(ResponseEntity.badRequest().build());
    }

    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Void>> deleteById(@PathVariable String id){
        log.info("deleteById executed {}", id);
        return clientTypeService.delete(id)
                .map( r -> ResponseEntity.ok().<Void>build())
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }
}
