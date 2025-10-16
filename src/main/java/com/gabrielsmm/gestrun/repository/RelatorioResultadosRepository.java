package com.gabrielsmm.gestrun.repository;

import com.gabrielsmm.gestrun.dto.ResultadoRelatorioDTO;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RelatorioResultadosRepository {
    List<ResultadoRelatorioDTO> buscarResultadoGeralPorCorrida(Long corridaId);
}
