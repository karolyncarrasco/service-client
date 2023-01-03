package com.bootcamp.client.web.mapper;

import com.bootcamp.client.domain.Client;
import com.bootcamp.client.web.model.ClientModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ClientMapper {
    @Mapping(target = "identityType.description", source = "identityType.description")
    @Mapping(target = "clientType.description", source = "clientType.description")
    Client modelToEntity(ClientModel model);

    @Mapping(target = "identityType.description", source = "identityType.description")
    @Mapping(target = "clientType.description", source = "clientType.description")
    ClientModel entityToModel(Client event);

    @Mapping(target = "id", ignore = true)
    void update(@MappingTarget Client entity, Client updateEntity);

}
