package com.spring_react.spring_react.service.impl;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.spring_react.spring_react.model.entity.Usuario;
import com.spring_react.spring_react.service.JwtService;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class JwtServiceImpl implements JwtService {

    //@Value("${jwt.expiracao}")
    private String expiracao = "30";

    //@Value("${jwt.chave-assinatura}")
    private String chaveAssinatura = "VHJ1bXAgw6kgbyBjYXJhIQ==";

    @Override
    public String gerarToken(Usuario usuario) {
        // TODO Auto-generated method stub

        long exp = Long.valueOf(expiracao);
        LocalDateTime dataHoraExpiracao = LocalDateTime.now().plusMinutes(exp);
        Instant instant = dataHoraExpiracao.atZone(ZoneId.systemDefault()).toInstant();
        Date data = Date.from(instant);

        String horaExpiracaoToken = dataHoraExpiracao.toLocalTime().format(DateTimeFormatter.ofPattern("HH:mm"));

        String token = Jwts.builder()
                            .setExpiration(data)
                .setSubject(usuario.getEmail())
                .claim(("userId"), usuario.getId())
                .claim("nome", usuario.getNome())
                .claim("horaExpiracao", horaExpiracaoToken)
                            .signWith(SignatureAlgorithm.HS512, chaveAssinatura)
                            .compact();
        return token;
   }

    @Override
    public Claims obterClaims(String token) throws ExpiredJwtException {
        // TODO Auto-generated method stub
        return Jwts.parser().setSigningKey(chaveAssinatura).parseClaimsJws(token).getBody();
     }

    @Override
    public boolean isTokenValido(String token) {
        // TODO Auto-generated method stub
        try {
                
            Claims claims = obterClaims(token);
            Date dataEx = claims.getExpiration();
            LocalDateTime dataExpiracao = dataEx.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
            return !LocalDateTime.now().isAfter(dataExpiracao);
                    
        } catch (Exception e) {
            return false;
            }
      }

    @Override
    public String obterLoginUsuario(String token) {
        // TODO Auto-generated method stub
        Claims claims = obterClaims(token);

        return claims.getSubject();
    }
    
}
