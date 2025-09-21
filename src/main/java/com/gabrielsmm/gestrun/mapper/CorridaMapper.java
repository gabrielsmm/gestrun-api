package com.gabrielsmm.gestrun.mapper;

import com.gabrielsmm.gestrun.domain.Corrida;
import com.gabrielsmm.gestrun.dto.CorridaInsertRequest;
import com.gabrielsmm.gestrun.dto.CorridaResponse;
import com.gabrielsmm.gestrun.dto.CorridaUpdateRequest;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface CorridaMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "organizador", ignore = true)
    @Mapping(target = "dataCriacao", ignore = true)
    Corrida toEntity(CorridaInsertRequest request);

    @Mapping(source = "organizador.id", target = "organizadorId")
    CorridaResponse toResponse(Corrida entity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromDto(CorridaUpdateRequest request, @MappingTarget Corrida entity);

}
