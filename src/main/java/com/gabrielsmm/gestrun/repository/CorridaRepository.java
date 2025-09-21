package com.gabrielsmm.gestrun.repository;

import com.gabrielsmm.gestrun.domain.Corrida;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CorridaRepository extends JpaRepository<Corrida, Long> {

    List<Corrida> findByOrganizadorId(Long organizadorId);

}
