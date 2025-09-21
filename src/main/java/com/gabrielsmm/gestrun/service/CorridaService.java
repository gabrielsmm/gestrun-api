package com.gabrielsmm.gestrun.service;

import com.gabrielsmm.gestrun.domain.Corrida;
import com.gabrielsmm.gestrun.domain.Usuario;
import com.gabrielsmm.gestrun.dto.CorridaInsertRequest;
import com.gabrielsmm.gestrun.dto.CorridaUpdateRequest;
import com.gabrielsmm.gestrun.exception.RecursoNaoEncontradoException;
import com.gabrielsmm.gestrun.mapper.CorridaMapper;
import com.gabrielsmm.gestrun.repository.CorridaRepository;
import lombok.RequiredArgsConstructor;
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

    public List<Corrida> listarPorOrganizador(Long organizadorId) {
        return corridaRepository.findByOrganizadorId(organizadorId);
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
        corridaMapper.updateEntityFromDto(request, atual);
        return corridaRepository.save(atual);
    }

    public void deletar(Long id) {
        if (!corridaRepository.existsById(id)) {
            throw new RecursoNaoEncontradoException("Corrida com id " + id + " não foi encontrado");
        }
        corridaRepository.deleteById(id);
    }

}
