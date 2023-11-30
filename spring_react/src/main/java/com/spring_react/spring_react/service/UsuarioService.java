package com.spring_react.spring_react.service;

import com.spring_react.spring_react.model.entity.Usuario;

public interface UsuarioService {

    Usuario autenticar(String email, String senha);

    Usuario cadastrar(Usuario usuario);

    void validarEmail(String email);
    
}
