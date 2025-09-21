package com.gabrielsmm.gestrun.domain.enums;

import lombok.Getter;

@Getter
public enum SexoCategoria {

    M("MASCULINO"),
    F("FEMININO"),
    A("AMBOS");

    private final String descricao;

    SexoCategoria(String descricao) {
        this.descricao = descricao;
    }

    public static SexoCategoria fromString(String text) {
        for (SexoCategoria x : SexoCategoria.values()) {
            if (x.descricao.equalsIgnoreCase(text) || x.name().equalsIgnoreCase(text)) {
                return x;
            }
        }
        return null;
    }

}
