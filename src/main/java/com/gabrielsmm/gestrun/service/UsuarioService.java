package com.gabrielsmm.gestrun.service;

import com.gabrielsmm.gestrun.domain.Usuario;
import com.gabrielsmm.gestrun.domain.enums.Perfil;
import com.gabrielsmm.gestrun.dto.UsuarioInsertRequest;
import com.gabrielsmm.gestrun.dto.UsuarioUpdateRequest;
import com.gabrielsmm.gestrun.exception.AcessoNegadoException;
import com.gabrielsmm.gestrun.exception.RecursoDuplicadoException;
import com.gabrielsmm.gestrun.exception.RecursoNaoEncontradoException;
import com.gabrielsmm.gestrun.mapper.UsuarioMapper;
import com.gabrielsmm.gestrun.repository.UsuarioRepository;
import com.gabrielsmm.gestrun.util.SecurityUtils;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final UsuarioMapper usuarioMapper;
    private final PasswordEncoder passwordEncoder;

    public List<Usuario> listar() {
        return usuarioRepository.findAll();
    }

    public Usuario buscarPorId(Long id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Usuário com id " + id + " não foi encontrado"));
    }

    public Usuario criar(UsuarioInsertRequest request) {
        if (usuarioRepository.existsByEmail(request.getEmail())) {
            throw new RecursoDuplicadoException("Email já cadastrado");
        }

        Usuario usuario = usuarioMapper.toEntity(request);
        usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));
        usuario.setDataCriacao(LocalDateTime.now());

        if (request.getPerfil() == null) {
            usuario.setPerfil(Perfil.ORGANIZADOR);
        } else if (request.getPerfil() == Perfil.ADMIN && !SecurityUtils.usuarioLogadoEhAdmin()) {
            throw new AcessoNegadoException("somente ADMIN pode criar outro ADMIN");
        } else {
            usuario.setPerfil(request.getPerfil());
        }

        return usuarioRepository.save(usuario);
    }

    @Transactional
    public Usuario atualizar(Long id, UsuarioUpdateRequest request) {
        Usuario atual = buscarPorId(id);

        if (request.getEmail() != null && !request.getEmail().equals(atual.getEmail())) {
            if (usuarioRepository.existsByEmail(request.getEmail())) {
                throw new RecursoDuplicadoException("Email já cadastrado");
            }
            atual.setEmail(request.getEmail());
        }

        usuarioMapper.updateEntityFromDto(request, atual);

        // se senha nova for fornecida, re-hash
        if (request.getSenha() != null && !request.getSenha().isBlank()) {
            atual.setSenha(passwordEncoder.encode(request.getSenha()));
        }

        if (request.getPerfil() != null && request.getPerfil() != atual.getPerfil()) {
            if (!SecurityUtils.usuarioLogadoEhAdmin()) {
                throw new AcessoNegadoException("Somente ADMIN pode alterar o perfil de um usuário");
            }
            atual.setPerfil(request.getPerfil());
        }

        return usuarioRepository.save(atual);
    }

    public void deletar(Long id) {
        if (!usuarioRepository.existsById(id)) {
            throw new RecursoNaoEncontradoException("Usuário com id " + id + " não foi encontrado");
        }
        usuarioRepository.deleteById(id);
    }

}
