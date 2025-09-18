package com.gabrielsmm.gestrun.mapper;

import com.gabrielsmm.gestrun.dto.UsuarioRequest;
import com.gabrielsmm.gestrun.dto.UsuarioResponse;
import com.gabrielsmm.gestrun.domain.Usuario;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface UsuarioMapper {

    Usuario toEntity(UsuarioRequest dto);

    UsuarioResponse toResponse(Usuario entity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromDto(UsuarioRequest dto, @MappingTarget Usuario entity);

}
