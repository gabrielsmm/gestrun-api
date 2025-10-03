package com.gabrielsmm.gestrun.repository;

import com.gabrielsmm.gestrun.domain.Categoria;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CategoriaRepository extends JpaRepository<Categoria, Long> {

    List<Categoria> findByCorridaId(Long corridaId);

    @Query("""
        SELECT c 
        FROM Categoria c 
        WHERE c.corrida.id = :corridaId 
        AND (:filtro IS NULL 
            OR LOWER(c.nome) LIKE LOWER(CONCAT('%', CAST(:filtro AS STRING), '%')) 
            )
    """)
    Page<Categoria> listarPorCorridaPaginado(@Param("corridaId") Long corridaId, @Param("filtro") String filtro, Pageable pageable);

}
