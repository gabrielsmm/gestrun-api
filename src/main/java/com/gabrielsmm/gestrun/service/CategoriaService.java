package com.gabrielsmm.gestrun.service;

import com.gabrielsmm.gestrun.domain.Categoria;
import com.gabrielsmm.gestrun.domain.Corrida;
import com.gabrielsmm.gestrun.dto.CategoriaInsertRequest;
import com.gabrielsmm.gestrun.dto.CategoriaUpdateRequest;
import com.gabrielsmm.gestrun.exception.AcessoNegadoException;
import com.gabrielsmm.gestrun.exception.RecursoNaoEncontradoException;
import com.gabrielsmm.gestrun.mapper.CategoriaMapper;
import com.gabrielsmm.gestrun.repository.CategoriaRepository;
import com.gabrielsmm.gestrun.util.SecurityUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class CategoriaService {

    private final CategoriaRepository categoriaRepository;
    private final CorridaService corridaService;
    private final CategoriaMapper categoriaMapper;

    public List<Categoria> listarPorCorrida(Long corridaId) {
        return categoriaRepository.findByCorridaId(corridaId);
    }

    public Categoria buscarPorId(Long id) {
        return categoriaRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Categoria com id " + id + " não foi encontrada"));
    }

    public Categoria criar(CategoriaInsertRequest request) {
        Corrida corrida = corridaService.buscarPorId(request.corridaId());

        if (!SecurityUtils.usuarioLogadoEhAdmin() &&
                !corrida.getOrganizador().getId().equals(SecurityUtils.getUsuarioIdLogado())) {
            throw new AcessoNegadoException("Você não tem permissão para adicionar categorias a esta corrida");
        }

        Categoria categoria = categoriaMapper.toEntity(request);
        categoria.setCorrida(corrida);

        return categoriaRepository.save(categoria);
    }

    public Categoria atualizar(Long id, CategoriaUpdateRequest request) {
        Categoria atual = buscarPorId(id);

        if (!SecurityUtils.usuarioLogadoEhAdmin() &&
                !atual.getCorrida().getOrganizador().getId().equals(SecurityUtils.getUsuarioIdLogado())) {
            throw new AcessoNegadoException("Você não tem permissão para atualizar esta categoria");
        }

        categoriaMapper.updateEntityFromDto(request, atual);
        return categoriaRepository.save(atual);
    }

    public void deletar(Long id) {
        Categoria categoria = buscarPorId(id);

        if (!SecurityUtils.usuarioLogadoEhAdmin() &&
                !categoria.getCorrida().getOrganizador().getId().equals(SecurityUtils.getUsuarioIdLogado())) {
            throw new AcessoNegadoException("Você não tem permissão para deletar esta categoria");
        }

        categoriaRepository.deleteById(id);
    }

}
