package com.gabrielsmm.gestrun.repository;

import com.gabrielsmm.gestrun.domain.Inscricao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InscricaoRepository extends JpaRepository<Inscricao, Long> {

    List<Inscricao> findByCorridaId(Long corridaId);

}
