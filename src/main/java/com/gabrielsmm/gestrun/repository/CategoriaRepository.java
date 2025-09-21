package com.gabrielsmm.gestrun.repository;

import com.gabrielsmm.gestrun.domain.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoriaRepository extends JpaRepository<Categoria, Long> {

    List<Categoria> findByCorridaId(Long corridaId);

}
