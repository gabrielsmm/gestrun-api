package com.gabrielsmm.gestrun.repository;

import com.gabrielsmm.gestrun.domain.Inscricao;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface InscricaoRepository extends JpaRepository<Inscricao, Long> {

    List<Inscricao> findByCorridaId(Long corridaId);

    @Query("""
        SELECT i
        FROM Inscricao i
        WHERE i.corrida.id = :corridaId
        AND (
            :filtro IS NULL
            OR LOWER(i.nomeCorredor) LIKE LOWER(CONCAT('%', CAST(:filtro AS STRING), '%'))
            OR LOWER(i.documento) LIKE LOWER(CONCAT('%', CAST(:filtro AS STRING), '%'))
            OR LOWER(i.email) LIKE LOWER(CONCAT('%', CAST(:filtro AS STRING), '%'))
            OR LOWER(i.telefone) LIKE LOWER(CONCAT('%', CAST(:filtro AS STRING), '%'))
            OR CAST(i.numeroPeito AS string) LIKE CONCAT('%', CAST(:filtro AS STRING), '%')
        )
    """)
    Page<Inscricao> listarPorCorridaPaginado(@Param("corridaId") Long corridaId, @Param("filtro") String filtro, Pageable pageable);

}
