package com.example.algamoney.api;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class CorsFilter implements Filter {
	
	//classe que verifica qual metodo http da requisição, caso seja options ele vai responder que ok.
	//Options é uma verificação feita pelos navegadores atraves do CORS em qualquer requisição ele vai verificar primeiro o OPTIONS E DEPOIS FAZER O METODO PEDIDO (GET POST E ETC)
	//Em resumo habilita qualquer requisição javascript autorizada pelo origem permitida.
	
	//Configurar de onde irá receber, produção, teste e etc.
	private String origemPermitida = "localhost:8080";
	
	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
			throws IOException, ServletException {
		
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;
		
		response.setHeader("Access-Control-Allow-Origin", origemPermitida);
		response.setHeader("Access-Control-Allow-Credentials", "true");
		
		if("OPTIONS".equals(request.getMethod()) && origemPermitida.equals(request.getHeader("Origin"))){
			response.setHeader("Access-Control-Allow-Methods", "POST, GET, DELETE, PUT, OPTIONS");
			response.setHeader("Access-Control-Allow-Headers", "Authorization, Content-Type, Accept");
			response.setHeader("Access-Control-Max-Age", "3600");
			response.setStatus(HttpServletResponse.SC_OK);
			
		}else {// caso não seja OPTIONS PASSA DIRETO CONTINUANDO A REQUISIÇÃO
			chain.doFilter(req, res);
		}
		
	}

}
