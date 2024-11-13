package com.spring_react.spring_react.service.impl;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;

import com.spring_react.spring_react.model.entity.Usuario;
import com.spring_react.spring_react.service.JwtService;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class JwtServiceImpl implements JwtService {

    @Value("${jwt.expiracao}")
    private String expiracao;

    @Value("${jwt.chave-assinatura}")
    private String chaveAssinatura;

    @Override
    public String gerarToken(Usuario usuario) {
        // TODO Auto-generated method stub

        long exp = Long.valueOf(expiracao);
        LocalDateTime dataHoraExpiracao = LocalDateTime.now().plusMinutes(exp);
        Instant instant = dataHoraExpiracao.atZone(ZoneId.systemDefault()).toInstant();
        Date data = Date.from(instant);

        String token = Jwts.builder()
                            .setExpiration(data)
                            .setSubject(usuario.getEmail())
                            .signWith(SignatureAlgorithm.HS512, chaveAssinatura)
                            .compact();
        return token;
   }

    @Override
    public Claims obterClaims(String token) throws ExpiredJwtException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'obterClaims'");
    }

    @Override
    public boolean isTokenValido(String token) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'isTokenValido'");
    }

    @Override
    public String obterLoginUsuario(String token) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'obterLoginUsuario'");
    }
    
}
