package com.example.algamoney.api.event;

import javax.servlet.http.HttpServletResponse;

import org.springframework.context.ApplicationEvent;


//ESTA CLASSE É RESPONSAVEL POR CRIAR O HEADER
public class RecursoCriadoEvent extends ApplicationEvent{

	private static final long serialVersionUID = 1L;

	//RECEBE O HTTP STATUS E O ID E MONTA O OBJETO RECURSO CRIADO... VIDE LISTENER
	private HttpServletResponse response;
	private Long id;

	public RecursoCriadoEvent(Object source, HttpServletResponse response, Long id) {
		super(source);
		this.id = id;
		this.response = response;		
	}
	
	
	public HttpServletResponse getResponse() {
		return response;
	}

	public Long getId() {
		return id;
	}

}
