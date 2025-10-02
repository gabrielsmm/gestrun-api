package com.gabrielsmm.gestrun.mapper;

import com.gabrielsmm.gestrun.domain.Corrida;
import com.gabrielsmm.gestrun.dto.CorridaInsertRequest;
import com.gabrielsmm.gestrun.dto.CorridaResponse;
import com.gabrielsmm.gestrun.dto.CorridaUpdateRequest;
import com.gabrielsmm.gestrun.dto.PaginacaoResponse;
import org.mapstruct.*;
import org.springframework.data.domain.Page;

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

    @Mapping(target = "conteudo", source = "content")
    @Mapping(target = "totalElementos", source = "totalElements")
    @Mapping(target = "totalPaginas", source = "totalPages")
    @Mapping(target = "tamanho", source = "size")
    @Mapping(target = "pagina", source = "number")
    PaginacaoResponse<CorridaResponse> toPaginacaoResponse(Page<Corrida> page);

}
