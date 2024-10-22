package com.spring_react.spring_react.model.repository;

import java.math.BigDecimal;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.spring_react.spring_react.model.entity.Lancamento;

@Repository
public interface LancamentoRepository extends JpaRepository<Lancamento, Long> {
	
	@Query(value =
			" select sum(l.valor) from Lancamento l join l.usuario u "
		+   " where u.id = :idUsuario and l.tipo = 'RECEITA'"
		+   " and l.status = 'EFETIVADO' group by u"	)
	BigDecimal obterSaldoReceitaPorUsuario(@Param("idUsuario") Long idUsuario);
	
	@Query(value =
			" select sum(l.valor) from Lancamento l join l.usuario u "
		+   " where u.id = :idUsuario  and l.tipo = 'DESPESA'"
		+   " and l.status = 'EFETIVADO' group by u"	)
	BigDecimal obterSaldoDespesaPorUsuario(@Param("idUsuario") Long idUsuario);
    
}
