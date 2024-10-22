package com.spring_react.spring_react.service;

import java.util.List;

import com.spring_react.spring_react.model.entity.Usuario;

public interface UsuarioService {

    Usuario autenticar(String email, String senha);

    Usuario salvarUsuario(Usuario usuario);

    List<Usuario> listarUsuarios();

    void validarEmail(String email);
    
    Usuario obterPorId(Long id);
    
}
