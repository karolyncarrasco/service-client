package com.bootcamp.client.web;

import com.bootcamp.client.domain.IdentityType;
import com.bootcamp.client.service.IdentityTypeService;
import com.bootcamp.client.web.mapper.IdentityTypeMapper;
import com.bootcamp.client.web.model.IdentityTypeModel;
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
@RequestMapping("/v1/identityType")
public class IdentityTypeController {
    @Value("${spring.application.name}")
    String name;

    @Value("${server.port}")
    String port;

    @Autowired
    private IdentityTypeService identityTypeService;


    @Autowired
    private IdentityTypeMapper identityTypeMapper;


    @GetMapping()
    public Mono<ResponseEntity<Flux<IdentityTypeModel>>> getAll(){
        log.info("getAll executed");
        return Mono.just(ResponseEntity.ok()
                .body(identityTypeService.findAll()
                        .map(Type -> identityTypeMapper.entityToModel(Type))));
    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<IdentityTypeModel>> getById(@PathVariable String id){
        log.info("getById executed {}", id);
        Mono<IdentityType> response = identityTypeService.findById(id);
        return response
                .map(Type -> identityTypeMapper.entityToModel(Type))
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Mono<ResponseEntity<IdentityTypeModel>> create(@Valid @RequestBody IdentityTypeModel request){
        log.info("create executed {}", request);
        return identityTypeService.create(identityTypeMapper.modelToEntity(request))
                .map(Type -> identityTypeMapper.entityToModel(Type))
                .flatMap(c -> Mono.just(ResponseEntity.created(URI.create(String.format("http://%s:%s/%s/%s", name, port, "IdentityType", c.getId())))
                        .body(c)))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public Mono<ResponseEntity<IdentityTypeModel>> updateById(@PathVariable String id, @Valid @RequestBody IdentityTypeModel request){
        log.info("updateById executed {}:{}", id, request);
        return identityTypeService.update(id, identityTypeMapper.modelToEntity(request))
                .map(Type -> identityTypeMapper.entityToModel(Type))
                .flatMap(c -> Mono.just(ResponseEntity.created(URI.create(String.format("http://%s:%s/%s/%s", name, port, "IdentityType", c.getId())))
                        .body(c)))
                .defaultIfEmpty(ResponseEntity.badRequest().build());
    }

    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Void>> deleteById(@PathVariable String id){
        log.info("deleteById executed {}", id);
        return identityTypeService.delete(id)
                .map( r -> ResponseEntity.ok().<Void>build())
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }
}
