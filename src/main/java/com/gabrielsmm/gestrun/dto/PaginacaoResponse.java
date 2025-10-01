package com.gabrielsmm.gestrun.dto;

import java.util.List;

public record PaginacaoResponse<T>(
        List<T> conteudo,
        long totalElementos,
        int totalPaginas,
        int tamanho,
        int pagina
) {}
