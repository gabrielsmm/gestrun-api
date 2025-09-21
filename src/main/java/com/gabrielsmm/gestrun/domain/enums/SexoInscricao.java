package com.gabrielsmm.gestrun.domain.enums;

import lombok.Getter;

@Getter
public enum SexoInscricao {

    M("MASCULINO"),
    F("FEMININO");

    private final String descricao;

    SexoInscricao(String descricao) {
        this.descricao = descricao;
    }

    public static SexoInscricao fromString(String text) {
        for (SexoInscricao x : SexoInscricao.values()) {
            if (x.descricao.equalsIgnoreCase(text) || x.name().equalsIgnoreCase(text)) {
                return x;
            }
        }
        return null;
    }

}
