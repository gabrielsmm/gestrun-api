package com.gabrielsmm.gestrun.mapper;

import com.gabrielsmm.gestrun.domain.Resultado;
import com.gabrielsmm.gestrun.dto.PaginacaoResponse;
import com.gabrielsmm.gestrun.dto.ResultadoInsertRequest;
import com.gabrielsmm.gestrun.dto.ResultadoResponse;
import com.gabrielsmm.gestrun.dto.ResultadoUpdateRequest;
import org.mapstruct.*;
import org.springframework.data.domain.Page;

@Mapper(componentModel = "spring")
public interface ResultadoMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "inscricao", ignore = true)
    Resultado toEntity(ResultadoInsertRequest request);

    @Mapping(source = "inscricao.id", target = "inscricaoId")
    @Mapping(source = "inscricao.nomeCorredor", target = "nomeCorredor")
    @Mapping(source = "inscricao.numeroPeito", target = "numeroPeito")
    ResultadoResponse toResponse(Resultado resultado);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromDto(ResultadoUpdateRequest request, @MappingTarget Resultado entity);

    @Mapping(target = "conteudo", source = "content")
    @Mapping(target = "totalElementos", source = "totalElements")
    @Mapping(target = "totalPaginas", source = "totalPages")
    @Mapping(target = "tamanho", source = "size")
    @Mapping(target = "pagina", source = "number")
    PaginacaoResponse<ResultadoResponse> toPaginacaoResponse(Page<Resultado> page);

}
