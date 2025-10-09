package com.gabrielsmm.gestrun.repository;

import com.gabrielsmm.gestrun.domain.Resultado;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ResultadoRepository extends JpaRepository<Resultado, Long> {

    boolean existsByInscricaoId(Long inscricaoId);

    @Query("""
        SELECT r FROM Resultado r
        WHERE r.inscricao.corrida.id = :corridaId
          AND (
            :filtro IS NULL
            OR LOWER(r.inscricao.nomeCorredor) LIKE LOWER(CONCAT('%', CAST(:filtro AS STRING), '%'))
            OR LOWER(r.inscricao.email) LIKE LOWER(CONCAT('%', CAST(:filtro AS STRING), '%'))
            OR LOWER(r.inscricao.documento) LIKE LOWER(CONCAT('%', CAST(:filtro AS STRING), '%'))
            OR CAST(r.inscricao.numeroPeito AS string) LIKE CONCAT('%', CAST(:filtro AS STRING), '%')
          )
    """)
    Page<Resultado> listarPorCorridaPaginadoOrdenadoPorTempo(@Param("corridaId") Long corridaId, @Param("filtro") String filtro, Pageable pageable);

}
