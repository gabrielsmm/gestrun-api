package com.gabrielsmm.gestrun.mappers;

import com.gabrielsmm.gestrun.dtos.UsuarioRequestDTO;
import com.gabrielsmm.gestrun.dtos.UsuarioResponseDTO;
import com.gabrielsmm.gestrun.entities.Usuario;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface UsuarioMapper {

    Usuario toEntity(UsuarioRequestDTO dto);

    UsuarioResponseDTO toResponse(Usuario entity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromDto(UsuarioRequestDTO dto, @MappingTarget Usuario entity);

}
