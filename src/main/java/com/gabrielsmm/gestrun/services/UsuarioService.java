package com.gabrielsmm.gestrun.services;

import com.gabrielsmm.gestrun.entities.Usuario;
import com.gabrielsmm.gestrun.repositories.UsuarioRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RequiredArgsConstructor
@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public Usuario criar(Usuario usuario) {
        if (usuarioRepository.existsByEmail(usuario.getEmail())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Email já cadastrado");
        }
        usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));
        return usuarioRepository.save(usuario);
    }

    public List<Usuario> listar() {
        return usuarioRepository.findAll();
    }

    public Usuario buscarPorId(Long id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado"));
    }

    @Transactional
    public Usuario atualizar(Long id, Usuario dados) {
        Usuario atual = buscarPorId(id);

        atual.setNome(dados.getNome());

        if (!atual.getEmail().equals(dados.getEmail())) {
            if (usuarioRepository.existsByEmail(dados.getEmail())) {
                throw new ResponseStatusException(HttpStatus.CONFLICT, "Email já cadastrado");
            }
            atual.setEmail(dados.getEmail());
        }

        // se senha nova for fornecida, re-hash
        if (dados.getSenha() != null && !dados.getSenha().isBlank()) {
            atual.setSenha(passwordEncoder.encode(dados.getSenha()));
        }

        return usuarioRepository.save(atual);
    }

    public void deletar(Long id) {
        if (!usuarioRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado");
        }
        usuarioRepository.deleteById(id);
    }

}
