package com.gabrielsmm.gestrun.domain;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Objects;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "resultados")
public class Resultado implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ToString.Exclude
    @OneToOne
    @JoinColumn(name = "inscricao_id", nullable = false, unique = true)
    private Inscricao inscricao;

    @Column(nullable = false)
    private LocalTime tempo;

    @Column(name = "posicao_geral")
    private Integer posicaoGeral;

    @Column(name = "data_criacao", nullable = false, updatable = false)
    private LocalDateTime dataCriacao;

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Resultado resultado = (Resultado) o;
        return Objects.equals(id, resultado.id) && Objects.equals(inscricao, resultado.inscricao) && Objects.equals(tempo, resultado.tempo) && Objects.equals(posicaoGeral, resultado.posicaoGeral) && Objects.equals(dataCriacao, resultado.dataCriacao);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, inscricao, tempo, posicaoGeral, dataCriacao);
    }

}
