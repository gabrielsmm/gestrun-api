package com.gabrielsmm.gestrun.repository;

import com.gabrielsmm.gestrun.domain.Inscricao;
import com.gabrielsmm.gestrun.domain.enums.StatusInscricao;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface InscricaoRepository extends JpaRepository<Inscricao, Long> {

    Optional<Inscricao> findByCorridaIdAndNumeroPeitoAndStatus(Long corridaId, Integer numeroPeito, StatusInscricao status);

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
