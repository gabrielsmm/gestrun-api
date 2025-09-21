package com.gabrielsmm.gestrun.domain;

import com.gabrielsmm.gestrun.domain.enums.SexoCategoria;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcType;
import org.hibernate.dialect.PostgreSQLEnumJdbcType;

import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "categorias")
public class Categoria implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "corrida_id", nullable = false)
    private Corrida corrida;

    @Column(nullable = false, length = 100)
    private String nome;

    @Column(name = "idade_min")
    private Integer idadeMin;

    @Column(name = "idade_max")
    private Integer idadeMax;

    @Enumerated(EnumType.STRING)
    @JdbcType(PostgreSQLEnumJdbcType.class)
    @Column(nullable = false, columnDefinition = "sexo_categoria_enum")
    private SexoCategoria sexo = SexoCategoria.A;

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Categoria categoria = (Categoria) o;
        return Objects.equals(id, categoria.id) && Objects.equals(corrida, categoria.corrida) && Objects.equals(nome, categoria.nome) && Objects.equals(idadeMin, categoria.idadeMin) && Objects.equals(idadeMax, categoria.idadeMax) && sexo == categoria.sexo;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, corrida, nome, idadeMin, idadeMax, sexo);
    }

}
