package com.gabrielsmm.gestrun.mapper;

import com.gabrielsmm.gestrun.domain.Resultado;
import com.gabrielsmm.gestrun.dto.ResultadoInsertRequest;
import com.gabrielsmm.gestrun.dto.ResultadoResponse;
import com.gabrielsmm.gestrun.dto.ResultadoUpdateRequest;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface ResultadoMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "inscricao", ignore = true)
    Resultado toEntity(ResultadoInsertRequest request);

    @Mapping(source = "inscricao.id", target = "inscricaoId")
    @Mapping(source = "inscricao.nomeCorredor", target = "nomeCorredor")
    ResultadoResponse toResponse(Resultado resultado);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromDto(ResultadoUpdateRequest request, @MappingTarget Resultado entity);

}
