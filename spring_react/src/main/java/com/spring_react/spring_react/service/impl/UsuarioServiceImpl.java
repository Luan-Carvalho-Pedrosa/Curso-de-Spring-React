package com.spring_react.spring_react.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring_react.spring_react.exceptions.RegraNegocioException;
import com.spring_react.spring_react.model.entity.Usuario;
import com.spring_react.spring_react.model.repository.UsuarioRepository;
import com.spring_react.spring_react.service.UsuarioService;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    private UsuarioRepository usuarioRepository;

    @Autowired
    public UsuarioServiceImpl(UsuarioRepository usuarioRepository) {
        super();
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public Usuario autenticar(String email, String senha) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'autenticar'");
    }

    @Override
    public Usuario cadastrar(Usuario usuario) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'cadastrar'");
    }

    @Override
    public void validarEmail(String email) {
        boolean existe = this.usuarioRepository.existisByEmail(email);
        if(existe){
            throw new RegraNegocioException("Email já existe");
        }


    }
    
}
