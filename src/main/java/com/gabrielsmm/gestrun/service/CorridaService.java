package com.gabrielsmm.gestrun.service;

import com.gabrielsmm.gestrun.domain.Corrida;
import com.gabrielsmm.gestrun.domain.Usuario;
import com.gabrielsmm.gestrun.dto.CorridaInsertRequest;
import com.gabrielsmm.gestrun.dto.CorridaUpdateRequest;
import com.gabrielsmm.gestrun.exception.AcessoNegadoException;
import com.gabrielsmm.gestrun.exception.RecursoNaoEncontradoException;
import com.gabrielsmm.gestrun.mapper.CorridaMapper;
import com.gabrielsmm.gestrun.repository.CorridaRepository;
import com.gabrielsmm.gestrun.util.SecurityUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CorridaService {

    private final CorridaRepository corridaRepository;
    private final UsuarioService usuarioService;
    private final CorridaMapper corridaMapper;

    public List<Corrida> listar() {
        return corridaRepository.findAll();
    }

    public Page<Corrida> listarPorOrganizadorPaginado(Long organizadorId, Integer pagina, Integer registrosPorPagina, String ordem, String direcao, String filtro) {
        PageRequest pageRequest = PageRequest.of(pagina, registrosPorPagina, Sort.Direction.valueOf(direcao), ordem);

        return corridaRepository.listarPorOrganizadorPaginado(organizadorId, filtro, pageRequest);
    }

    public Corrida buscarPorId(Long id) {
        return corridaRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Corrida com id " + id + " não foi encontrado"));
    }

    public Corrida criar(Long organizadorId, CorridaInsertRequest request) {
        Usuario organizador = usuarioService.buscarPorId(organizadorId);

        Corrida corrida = corridaMapper.toEntity(request);
        corrida.setOrganizador(organizador);
        corrida.setDataCriacao(LocalDateTime.now());

        return corridaRepository.save(corrida);
    }

    public Corrida atualizar(Long id, CorridaUpdateRequest request) {
        Corrida atual = buscarPorId(id);

        if (!SecurityUtils.usuarioLogadoEhAdmin() &&
                !atual.getOrganizador().getId().equals(SecurityUtils.getUsuarioIdLogado())) {
            throw new AcessoNegadoException("Você não tem permissão para atualizar esta corrida");
        }

        corridaMapper.updateEntityFromDto(request, atual);
        return corridaRepository.save(atual);
    }

    public void deletar(Long id) {
        Corrida corrida = buscarPorId(id);

        if (!SecurityUtils.usuarioLogadoEhAdmin() &&
                !corrida.getOrganizador().getId().equals(SecurityUtils.getUsuarioIdLogado())) {
            throw new AcessoNegadoException("Você não tem permissão para deletar esta corrida");
        }

        corridaRepository.deleteById(id);
    }

}
