package com.gabrielsmm.gestrun.mapper;

import com.gabrielsmm.gestrun.domain.Usuario;
import com.gabrielsmm.gestrun.dto.PaginacaoResponse;
import com.gabrielsmm.gestrun.dto.UsuarioInsertRequest;
import com.gabrielsmm.gestrun.dto.UsuarioResponse;
import com.gabrielsmm.gestrun.dto.UsuarioUpdateRequest;
import org.mapstruct.*;
import org.springframework.data.domain.Page;

@Mapper(componentModel = "spring")
public interface UsuarioMapper {

    Usuario toEntity(UsuarioInsertRequest dto);

    UsuarioResponse toResponse(Usuario entity);

    // Atualiza somente campos simples (nome, etc), ignorando nulls
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromDto(UsuarioUpdateRequest dto, @MappingTarget Usuario entity);

    @Mapping(target = "conteudo", source = "content")
    @Mapping(target = "totalElementos", source = "totalElements")
    @Mapping(target = "totalPaginas", source = "totalPages")
    @Mapping(target = "tamanho", source = "size")
    @Mapping(target = "pagina", source = "number")
    PaginacaoResponse<UsuarioResponse> toPaginacaoResponse(Page<Usuario> page);

}
