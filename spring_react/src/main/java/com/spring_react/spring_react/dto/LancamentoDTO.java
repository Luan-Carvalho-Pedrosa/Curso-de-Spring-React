package com.spring_react.spring_react.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LancamentoDTO {
	
	private Long id;
	private String descricao;
	private Long mes;
	private Long ano;
	private BigDecimal valor;
	private Long idUsuario;
	private String tipo;
	private String status;
	
	
	

}
