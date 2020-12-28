package com.example.algamoney.api.event.listener;

import java.net.URI;

import javax.servlet.http.HttpServletResponse;

import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.example.algamoney.api.event.RecursoCriadoEvent;

//ESTA CLASSE, ESCUTA QUALQUER EVENTO DA APLICAÇÃO, NESTE CASO ELA FICARA OUVINDO O EVENTO RecursoCriadoEvent QUE QUNADO ACONTECER, A PARTIR DO METODO DEFAULT
//QUE VEM DA CLASSE APPLICATIONLISTENER onApplicationEvent QUE VAI CHAMAR O EVENTO QUE QUEREMOS adicionarHeaderLocation ELE MONTA O HEADER E DEVOLVE...
@Component
public class RecursoCriadoListener implements ApplicationListener<RecursoCriadoEvent> {

	@Override
	public void onApplicationEvent(RecursoCriadoEvent event) {
		HttpServletResponse response = event.getResponse();
		Long id = event.getId();
		
		adicionarHeaderLocation(response, id);
	}

	private void adicionarHeaderLocation(HttpServletResponse response, Long id) {
		URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{codigo}")
				.buildAndExpand(id).toUri();
		response.setHeader("Location", uri.toASCIIString());
	}

}
