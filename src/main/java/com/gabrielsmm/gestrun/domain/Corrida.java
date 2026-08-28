package com.gabrielsmm.gestrun.domain;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "corridas")
public class Corrida implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "organizador_id", nullable = false)
    private Usuario organizador;

    @Column(nullable = false)
    private String nome;

    @Column(name = "data_hora_inicio", nullable = false)
    private LocalDateTime dataHoraInicio;

    @Column(nullable = false)
    private String local;

    @Column(name = "distancia_km", precision = 5, scale = 2)
    private BigDecimal distanciaKm;

    @Column(columnDefinition = "TEXT")
    private String regulamento;

    @Column(name = "valor_inscricao", nullable = false, precision = 10, scale = 2)
    private BigDecimal valorInscricao;

    @Column(name = "inscricoes_abertura", nullable = false)
    private LocalDateTime inscricoesAbertura;

    @Column(name = "inscricoes_encerramento", nullable = false)
    private LocalDateTime inscricoesEncerramento;

    @Column(nullable = false)
    private Integer capacidade;

    @Column(nullable = false)
    private boolean publicada;

    @Column(nullable = false, unique = true, updatable = false)
    private String slug;

    @Column(name = "data_criacao", nullable = false, updatable = false)
    private LocalDateTime dataCriacao;

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Corrida corrida = (Corrida) o;
        return Objects.equals(id, corrida.id) && Objects.equals(organizador, corrida.organizador) && Objects.equals(nome, corrida.nome) && Objects.equals(dataHoraInicio, corrida.dataHoraInicio) && Objects.equals(local, corrida.local) && Objects.equals(distanciaKm, corrida.distanciaKm) && Objects.equals(regulamento, corrida.regulamento) && Objects.equals(valorInscricao, corrida.valorInscricao) && Objects.equals(inscricoesAbertura, corrida.inscricoesAbertura) && Objects.equals(inscricoesEncerramento, corrida.inscricoesEncerramento) && Objects.equals(capacidade, corrida.capacidade) && publicada == corrida.publicada && Objects.equals(slug, corrida.slug) && Objects.equals(dataCriacao, corrida.dataCriacao);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, organizador, nome, dataHoraInicio, local, distanciaKm, regulamento, valorInscricao, inscricoesAbertura, inscricoesEncerramento, capacidade, publicada, slug, dataCriacao);
    }

}
