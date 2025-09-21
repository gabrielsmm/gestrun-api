package com.gabrielsmm.gestrun.domain;

import com.gabrielsmm.gestrun.domain.enums.SexoInscricao;
import com.gabrielsmm.gestrun.domain.enums.StatusInscricao;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.JdbcType;
import org.hibernate.dialect.PostgreSQLEnumJdbcType;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "inscricoes")
public class Inscricao implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "corrida_id", nullable = false)
    private Corrida corrida;

    @Column(nullable = false)
    private String nomeCorredor;

    private String documento;

    @Column(nullable = false)
    private LocalDate dataNascimento;

    @Enumerated(EnumType.STRING)
    @JdbcType(PostgreSQLEnumJdbcType.class)
    @Column(nullable = false, columnDefinition = "sexo_inscricao_enum")
    private SexoInscricao sexo;

    private String email;

    private String telefone;

    @Enumerated(EnumType.STRING)
    @JdbcType(PostgreSQLEnumJdbcType.class)
    @Column(nullable = false, columnDefinition = "inscricao_status_enum")
    private StatusInscricao status = StatusInscricao.PENDENTE;

    private Integer numeroPeito;

    @CreationTimestamp
    private LocalDateTime dataCriacao;

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Inscricao inscricao = (Inscricao) o;
        return Objects.equals(id, inscricao.id) && Objects.equals(corrida, inscricao.corrida) && Objects.equals(nomeCorredor, inscricao.nomeCorredor) && Objects.equals(documento, inscricao.documento) && Objects.equals(dataNascimento, inscricao.dataNascimento) && sexo == inscricao.sexo && Objects.equals(email, inscricao.email) && Objects.equals(telefone, inscricao.telefone) && status == inscricao.status && Objects.equals(numeroPeito, inscricao.numeroPeito) && Objects.equals(dataCriacao, inscricao.dataCriacao);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, corrida, nomeCorredor, documento, dataNascimento, sexo, email, telefone, status, numeroPeito, dataCriacao);
    }

}
