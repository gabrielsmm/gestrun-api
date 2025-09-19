package com.gabrielsmm.gestrun.mapper;

import com.gabrielsmm.gestrun.domain.Usuario;
import com.gabrielsmm.gestrun.dto.UsuarioInsertRequest;
import com.gabrielsmm.gestrun.dto.UsuarioResponse;
import com.gabrielsmm.gestrun.dto.UsuarioUpdateRequest;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface UsuarioMapper {

    Usuario toEntity(UsuarioInsertRequest dto);

    UsuarioResponse toResponse(Usuario entity);

    // Atualiza somente campos simples (nome, etc), ignorando nulls
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromDto(UsuarioUpdateRequest dto, @MappingTarget Usuario entity);

}
