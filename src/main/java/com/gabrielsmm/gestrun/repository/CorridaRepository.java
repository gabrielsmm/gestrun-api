package com.gabrielsmm.gestrun.repository;

import com.gabrielsmm.gestrun.domain.Corrida;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CorridaRepository extends JpaRepository<Corrida, Long> {

    List<Corrida> findByOrganizadorId(Long organizadorId);

    @Query("""
        SELECT c 
        FROM Corrida c 
        WHERE c.organizador.id = :organizadorId 
        AND (:filtro IS NULL 
            OR LOWER(c.nome) LIKE LOWER(CONCAT('%', CAST(:filtro AS STRING), '%')) 
            OR LOWER(c.local) LIKE LOWER(CONCAT('%', CAST(:filtro AS STRING), '%'))
            )
    """)
    Page<Corrida> listarPorOrganizadorPaginado(@Param("organizadorId") Long organizadorId, @Param("filtro") String filtro, Pageable pageable);

}
