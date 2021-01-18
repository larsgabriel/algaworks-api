package com.example.algamoney.api.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "permissao")
@Data
public class Permissao {
	
	@Id
	private Long id_permissao;
	private String descricao;
}
