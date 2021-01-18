package com.example.algamoney.api.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "usuario")
@Data
public class Usuario {
	
	@Id
	private Long id_usuario;
	
	private String nome;
	
	private String email;
	
	private String senha;
	
	@ManyToMany(fetch = FetchType.EAGER) //EAGER É TODO VEZ QUE TRAZER UM USUARIO TRAZER AS PERMISSOES JUNTO, SE FOSSE LAZY TRARIA SÓ SE PEDIR
	@JoinTable(name = "usuario_permissao", joinColumns = @JoinColumn(name="id_usuario"), inverseJoinColumns = @JoinColumn(name ="id_permissao"))
	private List<Permissao> permissoes;
	
}
