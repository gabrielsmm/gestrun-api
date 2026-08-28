package com.gabrielsmm.gestrun.service;

import com.gabrielsmm.gestrun.domain.Corrida;
import com.gabrielsmm.gestrun.domain.Usuario;
import com.gabrielsmm.gestrun.dto.CorridaInsertRequest;
import com.gabrielsmm.gestrun.dto.CorridaUpdateRequest;
import com.gabrielsmm.gestrun.exception.AcessoNegadoException;
import com.gabrielsmm.gestrun.exception.RecursoNaoEncontradoException;
import com.gabrielsmm.gestrun.exception.ValidacaoException;
import com.gabrielsmm.gestrun.mapper.CorridaMapper;
import com.gabrielsmm.gestrun.repository.CorridaRepository;
import com.gabrielsmm.gestrun.util.SecurityUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.text.Normalizer;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Locale;

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
                .orElseThrow(() -> new RecursoNaoEncontradoException("Corrida com id " + id + " não foi encontrada"));
    }

    public Corrida criar(Long organizadorId, CorridaInsertRequest request) {
        Usuario organizador = usuarioService.buscarPorId(organizadorId);
        Corrida corrida = corridaMapper.toEntity(request);
        corrida.setOrganizador(organizador);
        corrida.setDataCriacao(LocalDateTime.now());
        corrida.setPublicada(false);
        corrida.setSlug(gerarSlugUnico(request.nome()));
        validarDados(corrida);

        return corridaRepository.save(corrida);
    }

    public Corrida atualizar(Long id, CorridaUpdateRequest request) {
        Corrida atual = buscarPorId(id);
        validarPermissao(atual, "atualizar esta corrida");

        corridaMapper.updateEntityFromDto(request, atual);
        validarDados(atual);
        return corridaRepository.save(atual);
    }

    public Corrida alterarPublicacao(Long id, boolean publicada) {
        Corrida corrida = buscarPorId(id);
        validarPermissao(corrida, "alterar a publicação desta corrida");

        if (publicada) {
            validarDados(corrida);
        }

        corrida.setPublicada(publicada);
        return corridaRepository.save(corrida);
    }

    public void deletar(Long id) {
        Corrida corrida = buscarPorId(id);
        validarPermissao(corrida, "deletar esta corrida");
        corridaRepository.deleteById(id);
    }

    private void validarDados(Corrida corrida) {
        if (corrida.getNome() == null || corrida.getNome().isBlank()) {
            throw new ValidacaoException("O nome da corrida é obrigatório");
        }
        if (corrida.getLocal() == null || corrida.getLocal().isBlank()) {
            throw new ValidacaoException("O local da corrida é obrigatório");
        }
        if (corrida.getDataHoraInicio() == null) {
            throw new ValidacaoException("A data e hora de início da corrida são obrigatórias");
        }
        if (corrida.getValorInscricao() == null || corrida.getValorInscricao().signum() < 0) {
            throw new ValidacaoException("O valor da inscrição não pode ser negativo");
        }
        if (corrida.getCapacidade() == null || corrida.getCapacidade() < 0) {
            throw new ValidacaoException("A capacidade não pode ser negativa");
        }
        if (corrida.getInscricoesAbertura() == null || corrida.getInscricoesEncerramento() == null ||
                !corrida.getInscricoesEncerramento().isAfter(corrida.getInscricoesAbertura())) {
            throw new ValidacaoException("O encerramento das inscrições deve ser posterior à abertura");
        }
        if (!corrida.getInscricoesEncerramento().isBefore(corrida.getDataHoraInicio())) {
            throw new ValidacaoException("O encerramento das inscrições deve ser anterior ao início da corrida");
        }
    }

    private void validarPermissao(Corrida corrida, String acao) {
        if (!SecurityUtils.usuarioLogadoEhAdmin() &&
                !corrida.getOrganizador().getId().equals(SecurityUtils.getUsuarioIdLogado())) {
            throw new AcessoNegadoException("Você não tem permissão para " + acao);
        }
    }

    private String gerarSlugUnico(String nome) {
        String base = Normalizer.normalize(nome, Normalizer.Form.NFD)
                .replaceAll("\\p{M}", "")
                .toLowerCase(Locale.ROOT)
                .replaceAll("[^a-z0-9]+", "-")
                .replaceAll("(^-|-$)", "");
        if (base.isBlank()) {
            base = "corrida";
        }

        String slug = base;
        int sufixo = 2;
        while (corridaRepository.existsBySlug(slug)) {
            slug = base + "-" + sufixo++;
        }
        return slug;
    }
}
