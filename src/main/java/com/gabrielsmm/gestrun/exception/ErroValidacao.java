package com.gabrielsmm.gestrun.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
public class ErroValidacao extends ErroResposta {

    private final List<ErroCampo> erros = new ArrayList<>();

    public ErroValidacao(LocalDateTime timestamp, int status, String erro, String mensagem, String caminho) {
        super(timestamp, status, erro, mensagem, caminho);
    }

    public void adicionarErro(String campo, String mensagem) {
        this.erros.add(new ErroCampo(campo, mensagem));
    }

    @AllArgsConstructor
    @Getter
    public static class ErroCampo {

        private String campo;
        private String mensagem;

    }
}
