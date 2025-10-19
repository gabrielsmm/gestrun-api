package com.gabrielsmm.gestrun.service;

import com.gabrielsmm.gestrun.domain.Corrida;
import com.gabrielsmm.gestrun.domain.Inscricao;
import com.gabrielsmm.gestrun.domain.enums.StatusInscricao;
import com.gabrielsmm.gestrun.dto.InscricaoInsertRequest;
import com.gabrielsmm.gestrun.dto.InscricaoUpdateRequest;
import com.gabrielsmm.gestrun.exception.AcessoNegadoException;
import com.gabrielsmm.gestrun.exception.RecursoNaoEncontradoException;
import com.gabrielsmm.gestrun.exception.ValidacaoException;
import com.gabrielsmm.gestrun.mapper.InscricaoMapper;
import com.gabrielsmm.gestrun.repository.InscricaoRepository;
import com.gabrielsmm.gestrun.util.SecurityUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class InscricaoService {

    private final InscricaoRepository inscricaoRepository;
    private final CorridaService corridaService;
    private final InscricaoMapper inscricaoMapper;

    public Page<Inscricao> listarPorCorridaPaginado(Long corridaId, Integer pagina, Integer registrosPorPagina, String ordem, String direcao, String filtro) {
        Corrida corrida = corridaService.buscarPorId(corridaId);

        if (!SecurityUtils.usuarioLogadoEhAdmin() &&
                !corrida.getOrganizador().getId().equals(SecurityUtils.getUsuarioIdLogado())) {
            throw new AcessoNegadoException("Você não tem permissão para listar as inscrições desta corrida");
        }

        PageRequest pageRequest = PageRequest.of(pagina, registrosPorPagina, Sort.Direction.valueOf(direcao), ordem);

        return inscricaoRepository.listarPorCorridaPaginado(corridaId, filtro, pageRequest);
    }

    public Inscricao buscarPorId(Long id) {
        return inscricaoRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Inscrição com id " + id + " não foi encontrada"));
    }

    public Inscricao buscarConfirmadaPorCorridaENumeroPeito(Long corridaId, Integer numeroPeito) {
        return inscricaoRepository.findByCorridaIdAndNumeroPeitoAndStatus(corridaId, numeroPeito, StatusInscricao.CONFIRMADA)
                .orElseThrow(() ->
                        new RecursoNaoEncontradoException("Inscrição confirmada com número de peito " + numeroPeito + " não foi encontrada para a corrida " + corridaId));
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

        if (request.numeroPeito() != null &&
                inscricaoRepository.existsByCorridaIdAndNumeroPeitoAndIdNot(atual.getCorrida().getId(), request.numeroPeito(), id)) {
            throw new ValidacaoException("Número de peito " + request.numeroPeito() + " já está atribuído a outra inscrição nesta corrida");
        }

        if (request.numeroPeito() != null && request.status() == StatusInscricao.PENDENTE) {
            throw new ValidacaoException("Para atribuir um número de peito, a inscrição deve estar confirmada");
        }

        if (request.numeroPeito() == null && request.status() == StatusInscricao.CONFIRMADA) {
            throw new ValidacaoException("Para confirmar uma inscrição, é necessário atribuir um número de peito");
        }

        inscricaoMapper.updateEntityFromDto(request, atual);

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
