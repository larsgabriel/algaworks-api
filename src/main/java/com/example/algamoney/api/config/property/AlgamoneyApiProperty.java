package com.example.algamoney.api.config.property;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;

@ConfigurationProperties("algamoney")
@Data
public class AlgamoneyApiProperty {
	
	private final Seguranca seguranca = new Seguranca();
	
	private String origemPermitida = "localhost:8080";
	
	public static class Seguranca {
		
		private boolean enableHttps;
		
		public boolean isEnableHttps() {
			return enableHttps;
		}
		
		public void setEnableHttps(boolean enableHttps) {
			this.enableHttps = enableHttps;
		}
	}
	
}
