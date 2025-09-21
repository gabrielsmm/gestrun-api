package com.gabrielsmm.gestrun.repository;

import com.gabrielsmm.gestrun.domain.Inscricao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface InscricaoRepository extends JpaRepository<Inscricao, Long> {

    List<Inscricao> findByCorridaId(Long corridaId);

    Optional<Inscricao> findByIdAndCorridaId(Long id, Long corridaId);

}
