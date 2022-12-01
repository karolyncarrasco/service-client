package com.bootcamp.client.web.mapper;

import com.bootcamp.client.domain.ClientType;
import com.bootcamp.client.web.model.ClientTypeModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ClientTypeMapper {
    ClientType modelToEntity (ClientTypeModel model);

    ClientTypeModel entityToModel (ClientType event);

    @Mapping(target = "id", ignore = true)
    void update(@MappingTarget ClientType entity, ClientType updateEntity);
}
