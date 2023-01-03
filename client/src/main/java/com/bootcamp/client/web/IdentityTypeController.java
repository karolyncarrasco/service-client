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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
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
    private String name;

    @Value("${server.port}")
    private String port;

    @Autowired
    private IdentityTypeService identityTypeService;


    @Autowired
    private IdentityTypeMapper identityTypeMapper;


    @GetMapping()
    public Mono<ResponseEntity<Flux<IdentityTypeModel>>> getAll() {
        log.info("getAll executed");
        return Mono.just(ResponseEntity.ok()
                .body(identityTypeService.findAll()
                        .map(type -> identityTypeMapper.entityToModel(type))));
    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<IdentityTypeModel>> getById(@PathVariable String id) {
        log.info("getById executed {}", id);
        Mono<IdentityType> response = identityTypeService.findById(id);
        return response
                .map(type -> identityTypeMapper.entityToModel(type))
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Mono<ResponseEntity<IdentityTypeModel>> create(@Valid @RequestBody IdentityTypeModel request) {
        log.info("create executed {}", request);
        return identityTypeService.create(identityTypeMapper.modelToEntity(request))
                .map(type -> identityTypeMapper.entityToModel(type))
                .flatMap(c -> Mono.just(ResponseEntity.created(URI.create(String.format("http://%s:%s/%s/%s", name, port, "IdentityType", c.getId())))
                        .body(c)))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public Mono<ResponseEntity<IdentityTypeModel>> updateById(@PathVariable String id, @Valid @RequestBody IdentityTypeModel request) {
        log.info("updateById executed {}:{}", id, request);
        return identityTypeService.update(id, identityTypeMapper.modelToEntity(request))
                .map(type -> identityTypeMapper.entityToModel(type))
                .flatMap(c -> Mono.just(ResponseEntity.created(URI.create(String.format("http://%s:%s/%s/%s", name, port, "IdentityType", c.getId())))
                        .body(c)))
                .defaultIfEmpty(ResponseEntity.badRequest().build());
    }

    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Void>> deleteById(@PathVariable String id) {
        log.info("deleteById executed {}", id);
        return identityTypeService.delete(id)
                .map(r -> ResponseEntity.ok().<Void>build())
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }
}
