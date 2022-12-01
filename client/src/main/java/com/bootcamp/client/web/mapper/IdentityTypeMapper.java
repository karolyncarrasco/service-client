package com.bootcamp.client.web.mapper;

import com.bootcamp.client.domain.IdentityType;
import com.bootcamp.client.web.model.IdentityTypeModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface IdentityTypeMapper {
    IdentityType modelToEntity (IdentityTypeModel model);

    IdentityTypeModel entityToModel (IdentityType event);

    @Mapping(target = "id", ignore = true)
    void update(@MappingTarget IdentityType entity, IdentityType updateEntity);
}
