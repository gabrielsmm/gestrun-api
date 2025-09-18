package com.gabrielsmm.gestrun.mapper;

import com.gabrielsmm.gestrun.dto.UsuarioRequestDTO;
import com.gabrielsmm.gestrun.dto.UsuarioResponseDTO;
import com.gabrielsmm.gestrun.domain.Usuario;
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
