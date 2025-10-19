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
                r.posicaoGeral,
                i.numeroPeito,
                i.nomeCorredor,
                CAST((YEAR(CURRENT_DATE) - YEAR(i.dataNascimento)) AS integer),
                CAST(i.sexo AS string),
                CAST(r.tempo AS string)
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

    @Override
    public List<ResultadoRelatorioDTO> buscarResultadoPorCorridaEPorCategoria(Long corridaId, Long categoriaId) {
        // Usando subconsulta para calcular a posição dentro da categoria
        String jpql = """
            SELECT new com.gabrielsmm.gestrun.dto.ResultadoRelatorioDTO(
                CAST(((SELECT COUNT(r2) FROM Resultado r2
                       JOIN r2.inscricao i2
                       WHERE i2.corrida = i.corrida
                         AND CAST((YEAR(CURRENT_DATE) - YEAR(i2.dataNascimento)) AS integer) BETWEEN c.idadeMin AND c.idadeMax
                         AND (c.sexo = 'A' OR CAST(i2.sexo AS string) = CAST(c.sexo AS string))
                         AND r2.tempo < r.tempo
                     ) + 1) AS integer),
                i.numeroPeito,
                i.nomeCorredor,
                CAST((YEAR(CURRENT_DATE) - YEAR(i.dataNascimento)) AS integer),
                CAST(i.sexo AS string),
                CAST(r.tempo AS string)
            )
            FROM Resultado r
            JOIN r.inscricao i, Categoria c
            WHERE c.corrida = i.corrida
              AND i.corrida.id = :corridaId
              AND c.id = :categoriaId
              AND CAST((YEAR(CURRENT_DATE) - YEAR(i.dataNascimento)) AS integer) BETWEEN c.idadeMin AND c.idadeMax
              AND (c.sexo = 'A' OR CAST(i.sexo AS string) = CAST(c.sexo AS string))
            ORDER BY r.tempo
        """;

        return entityManager.createQuery(jpql, ResultadoRelatorioDTO.class)
                .setParameter("corridaId", corridaId)
                .setParameter("categoriaId", categoriaId)
                .getResultList();
    }

}
