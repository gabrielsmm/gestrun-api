package com.gabrielsmm.gestrun.repository;

import com.gabrielsmm.gestrun.dto.ResultadoRelatorioDTO;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class RelatorioResultadosRepositoryImpl implements RelatorioResultadosRepository {

    @PersistenceContext
    private EntityManager entityManager;

    public List<ResultadoRelatorioDTO> buscarResultadoGeralPorCorrida(Long corridaId) {
        String jpql = """
            SELECT new com.gabrielsmm.gestrun.dto.ResultadoRelatorioDTO(
                r.posicaoGeral, i.numeroPeito, i.nomeCorredor, r.tempo
            )
            FROM Resultado r
            JOIN r.inscricao i
            WHERE i.corrida.id = :corridaId
            ORDER BY r.posicaoGeral
        """;

        return entityManager.createQuery(jpql, ResultadoRelatorioDTO.class)
                .setParameter("corridaId", corridaId)
                .getResultList();
    }
}
