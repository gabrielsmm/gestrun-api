package com.gabrielsmm.gestrun.mapper;

import com.gabrielsmm.gestrun.domain.Inscricao;
import com.gabrielsmm.gestrun.dto.InscricaoInsertRequest;
import com.gabrielsmm.gestrun.dto.InscricaoResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

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

}
