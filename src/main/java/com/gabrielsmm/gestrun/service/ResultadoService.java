package com.gabrielsmm.gestrun.service;

import com.gabrielsmm.gestrun.domain.Inscricao;
import com.gabrielsmm.gestrun.domain.Resultado;
import com.gabrielsmm.gestrun.dto.ResultadoInsertRequest;
import com.gabrielsmm.gestrun.dto.ResultadoUpdateRequest;
import com.gabrielsmm.gestrun.exception.RecursoDuplicadoException;
import com.gabrielsmm.gestrun.exception.RecursoNaoEncontradoException;
import com.gabrielsmm.gestrun.mapper.ResultadoMapper;
import com.gabrielsmm.gestrun.repository.ResultadoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Service
public class ResultadoService {

    private final ResultadoRepository resultadoRepository;
    private final InscricaoService inscricaoService;
    private final ResultadoMapper resultadoMapper;

    public List<Resultado> listarPorCorrida(Long corridaId) {
        return resultadoRepository.findAllByInscricaoCorridaIdOrderByTempoAsc(corridaId);
    }

    public Resultado buscarPorId(Long id) {
        return resultadoRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Resultado com id " + id + " não foi encontrado"));
    }

    public Resultado criar(ResultadoInsertRequest request) {
        Inscricao inscricao = inscricaoService.buscarPorId(request.inscricaoId());

        if (resultadoRepository.existsByInscricaoId(inscricao.getId())) {
            throw new RecursoDuplicadoException("Já existe um resultado cadastrado para a inscrição com id " + inscricao.getId());
        }

        Resultado resultado = resultadoMapper.toEntity(request);
        resultado.setInscricao(inscricao);
        resultado.setDataCriacao(LocalDateTime.now());

        return resultadoRepository.save(resultado);
    }

    public Resultado atualizar(Long id, ResultadoUpdateRequest request) {
        Resultado atual = buscarPorId(id);

        resultadoMapper.updateEntityFromDto(request, atual);

        return resultadoRepository.save(atual);
    }

    public void deletar(Long id) {
        if (!resultadoRepository.existsById(id)) {
            throw new RecursoNaoEncontradoException("Resultado com id " + id + " não foi encontrado");
        }
        resultadoRepository.deleteById(id);
    }

}
