package com.gabrielsmm.gestrun.service;

import com.gabrielsmm.gestrun.domain.Corrida;
import com.gabrielsmm.gestrun.domain.Inscricao;
import com.gabrielsmm.gestrun.dto.InscricaoInsertRequest;
import com.gabrielsmm.gestrun.dto.InscricaoUpdateRequest;
import com.gabrielsmm.gestrun.exception.AcessoNegadoException;
import com.gabrielsmm.gestrun.exception.RecursoNaoEncontradoException;
import com.gabrielsmm.gestrun.mapper.InscricaoMapper;
import com.gabrielsmm.gestrun.repository.InscricaoRepository;
import com.gabrielsmm.gestrun.util.SecurityUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class InscricaoService {

    private final InscricaoRepository inscricaoRepository;
    private final CorridaService corridaService;
    private final InscricaoMapper inscricaoMapper;

    public List<Inscricao> listarPorCorrida(Long corridaId) {
        Corrida corrida = corridaService.buscarPorId(corridaId);

        if (!SecurityUtils.usuarioLogadoEhAdmin() &&
                !corrida.getOrganizador().getId().equals(SecurityUtils.getUsuarioIdLogado())) {
            throw new AcessoNegadoException("Você não tem permissão para listar as inscrições desta corrida");
        }

        return inscricaoRepository.findByCorridaId(corridaId);
    }

    public Inscricao buscarPorId(Long id) {
        return inscricaoRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Inscrição com id " + id + " não foi encontrada"));
    }

    public Inscricao buscarPublico(Long inscricaoId, String documentoOuEmail) {
        Inscricao inscricao = buscarPorId(inscricaoId);

        if (!(documentoOuEmail.equalsIgnoreCase(inscricao.getDocumento()) ||
                documentoOuEmail.equalsIgnoreCase(inscricao.getEmail()))) {
            throw new AcessoNegadoException("Dados de validação não conferem");
        }

        return inscricao;
    }

    public Inscricao criar(InscricaoInsertRequest request) {
        Corrida corrida = corridaService.buscarPorId(request.corridaId());
        Inscricao inscricao = inscricaoMapper.toEntity(request);
        inscricao.setCorrida(corrida);
        return inscricaoRepository.save(inscricao);
    }

    public Inscricao atualizar(Long id, InscricaoUpdateRequest request) {
        Inscricao atual = buscarPorId(id);

        if (!SecurityUtils.usuarioLogadoEhAdmin() &&
                !atual.getCorrida().getOrganizador().getId().equals(SecurityUtils.getUsuarioIdLogado())) {
            throw new AcessoNegadoException("Você não tem permissão para atualizar esta inscrição");
        }

        if (request.status() != null) {
            atual.setStatus(request.status());
        }

        if (request.numeroPeito() != null) {
            atual.setNumeroPeito(request.numeroPeito());
        }

        return inscricaoRepository.save(atual);
    }

    public void deletar(Long id) {
        Inscricao inscricao = buscarPorId(id);

        if (!SecurityUtils.usuarioLogadoEhAdmin() &&
                !inscricao.getCorrida().getOrganizador().getId().equals(SecurityUtils.getUsuarioIdLogado())) {
            throw new AcessoNegadoException("Você não tem permissão para deletar esta inscrição");
        }

        inscricaoRepository.deleteById(id);
    }

}
