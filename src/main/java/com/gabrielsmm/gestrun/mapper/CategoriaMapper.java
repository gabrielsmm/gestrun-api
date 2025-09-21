package com.gabrielsmm.gestrun.mapper;

import com.gabrielsmm.gestrun.domain.Categoria;
import com.gabrielsmm.gestrun.dto.CategoriaInsertRequest;
import com.gabrielsmm.gestrun.dto.CategoriaResponse;
import com.gabrielsmm.gestrun.dto.CategoriaUpdateRequest;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface CategoriaMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "corrida", ignore = true)
    Categoria toEntity(CategoriaInsertRequest request);

    @Mapping(source = "corrida.id", target = "corridaId")
    CategoriaResponse toResponse(Categoria entity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromDto(CategoriaUpdateRequest request, @MappingTarget Categoria entity);

}
