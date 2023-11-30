package com.spring_react.spring_react.model.repository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.spring_react.spring_react.model.entity.Usuario;

import junit.framework.Assert;


@SpringBootTest
@RunWith(SpringRunner.class)
@ActiveProfiles("test")
public class UsuarioRepositoryTest {


    @Autowired
    UsuarioRepository repository;

    @Test
    public void  deveVerificarAExistenciaDeUmEmail(){
        //cenário
        Usuario usuario = new Usuario("usuario", "usuario@email.com", "12345");
        repository.save(usuario);

        //ação/execucao
        boolean result = repository.existisByEmail("usuario@email.com");

        //verificacao
        Assert.assertEquals(true, result);

    }
    
}
