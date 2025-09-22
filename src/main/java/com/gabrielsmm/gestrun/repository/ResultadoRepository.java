package com.gabrielsmm.gestrun.repository;

import com.gabrielsmm.gestrun.domain.Resultado;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ResultadoRepository extends JpaRepository<Resultado, Long> {

    List<Resultado> findAllByInscricaoCorridaIdOrderByTempoAsc(Long corridaId);

    boolean existsByInscricaoId(Long inscricaoId);

}
