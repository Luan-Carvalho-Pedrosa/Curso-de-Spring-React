package com.spring_react.spring_react.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.spring_react.spring_react.exceptions.ErroAutenticacao;
import com.spring_react.spring_react.exceptions.RegraNegocioException;
import com.spring_react.spring_react.model.entity.Usuario;
import com.spring_react.spring_react.model.repository.UsuarioRepository;
import com.spring_react.spring_react.service.UsuarioService;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;
    
    

    public UsuarioServiceImpl(UsuarioRepository usuarioRepository) {
		super();
		this.usuarioRepository = usuarioRepository;
	}

	@Override
    public Usuario autenticar(String email, String senha) {
    	Optional<Usuario> usuario = this.usuarioRepository.findByEmail(email);
    	
    	if(!usuario.isPresent()) {
    		throw new ErroAutenticacao("Usuario não encontrado para o email informado.");
    	}
    	
    	if(!usuario.get().getSenha().equals(senha)) {
    		throw new ErroAutenticacao("Senha invalida.");

    	}
    	
    	return usuario.get();
    }

    @Override
    @Transactional
    public Usuario salvarUsuario(Usuario usuario) {
    	validarEmail(usuario.getEmail());
    	return this.usuarioRepository.save(usuario);
    }

    @Override
    public void validarEmail(String email) {
    	
        boolean existe = this.validarEmailBoolean(email);
        if(existe){
            throw new RegraNegocioException("Email já existe");
        }
        
    }
    
    public boolean validarEmailBoolean(String email) {
    	return this.usuarioRepository.findByEmail(email).isPresent();
    	
    }

	@Override
	public Usuario obterPorId(Long id) {
		// TODO Auto-generated method stub
		return this.usuarioRepository.findById(id).get();
	}

	@Override
	public List<Usuario> listarUsuarios() {
		// TODO Auto-generated method stub
		return this.usuarioRepository.findAll();
	}
    
}
