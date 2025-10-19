package com.gabrielsmm.gestrun.mapper;

import com.gabrielsmm.gestrun.domain.Inscricao;
import com.gabrielsmm.gestrun.dto.InscricaoInsertRequest;
import com.gabrielsmm.gestrun.dto.InscricaoResponse;
import com.gabrielsmm.gestrun.dto.InscricaoUpdateRequest;
import com.gabrielsmm.gestrun.dto.PaginacaoResponse;
import org.mapstruct.*;
import org.springframework.data.domain.Page;

@Mapper(componentModel = "spring")
public interface InscricaoMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "corrida", ignore = true)
    @Mapping(target = "status", constant = "PENDENTE")
    @Mapping(target = "numeroPeito", ignore = true)
    @Mapping(target = "dataCriacao", ignore = true)
    Inscricao toEntity(InscricaoInsertRequest request);

    @Mapping(source = "corrida.id", target = "corridaId")
    InscricaoResponse toResponse(Inscricao  inscricao);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromDto(InscricaoUpdateRequest request, @MappingTarget Inscricao entity);

    @Mapping(target = "conteudo", source = "content")
    @Mapping(target = "totalElementos", source = "totalElements")
    @Mapping(target = "totalPaginas", source = "totalPages")
    @Mapping(target = "tamanho", source = "size")
    @Mapping(target = "pagina", source = "number")
    PaginacaoResponse<InscricaoResponse> toPaginacaoResponse(Page<Inscricao> page);

}
