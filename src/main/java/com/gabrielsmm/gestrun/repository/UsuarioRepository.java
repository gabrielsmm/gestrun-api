package com.gabrielsmm.gestrun.repository;

import com.gabrielsmm.gestrun.domain.Usuario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    Optional<Usuario> findByEmail(String email);

    boolean existsByEmail(String email);

    @Query("""
      select u
      from Usuario u
      where (:filtro is null
         or lower(u.nome) like lower(concat('%', cast(:filtro as string), '%'))
         or lower(u.email) like lower(concat('%', cast(:filtro as string), '%')))
    """)
    Page<Usuario> listarPaginado(@Param("filtro") String filtro, Pageable pageable);

}