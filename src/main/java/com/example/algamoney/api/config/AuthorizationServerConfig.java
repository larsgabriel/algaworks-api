package com.example.algamoney.api.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {
	
	//CLASSE RESPONSAVEL POR GERAR O TOKEN
	
	@Autowired
	@Qualifier("authenticationManagerBean")
	AuthenticationManager authenticationManager;
	
	@Autowired
	private UserDetailsServiceImpl userDetailsService;

	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
		PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
		clients.inMemory() //EM MEMORIA NÃO É BANCO DE DADOS
			//withClient = CLIENT/APLICAÇÃO/SERVIÇO QUE VAI PEDIR TOKEN, NO CASO EXEMPLO SERIA UMA APLICAÇÃO ANGULAR. AS INFORMAÇÕES
			//TEM QUE SER AS MESMAS DO POSTMAN. NO POSTMAN SERÁ CONFIGURADO UM POST COM ESTA URL localhost:8080/oauth/token
			//AUTHORIZATION TYPE BASIC AUTH USERNAME angular PASSWORD linha debaixo secret. Será então gerado um token
			.withClient("angular") 
			.secret(encoder.encode("@ngul@r0")) //SENHA DO CLIENT
			.scopes("read", "write")
			//refresh_token é um granttype que implementa o refresh token.
			//QUANDO É GERADO UM PRIMEIRO TOKEN COMUM, ELE DURA 20 SEGUNDOS. MAS NA MESMA REQUISIÇÃO QUE VEM O TOKEN COMUM VEM UM CARA CHAMADO REFRESH TOKEN,
			//USANDO ESSE CARA PARA FAZER UMA NOVA REQUISIÇÃO ELE GERA UM NOVO TOKEN COM ID DE ACCESS_TOKEN QUE ESSE CARA PODE SER USADO NA REQUISIÇÃO
			.authorizedGrantTypes("password", "refresh_token")
			//TEMPO DE DURAÇÃO DO TOKEN
			.accessTokenValiditySeconds(20)
			//esse tempo de duração de refresh token.. esta conta da 1 dia, então o usuario vai poder ficar 1 dia sem ter que logar novamente.
			//O refresh token vai gerar um novo access token que não precisa de login e senha, ou seja o usuario não vai precisar se logar de novo, ele vai gerar um novo access token
			// e isso dentro de cockies não sera possivel pegar via javascript
			.refreshTokenValiditySeconds(3600 * 24);
	}
	
//	@Override //ESTE MÉTODO FUNCIONA PARA VALIDAÇÃO DE TOKEN IN MEMORIA
//	public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
//		endpoints
//		.tokenStore(tokenStore())
//		.authenticationManager(authenticationManager);
//	}
	
	
	//ESTE MÉTODO PARA JWT verificar o token gerado no  https://jwt.io/
	@Override 
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
		endpoints
		.tokenStore(tokenStore())
		//CONVERSOR DE TOKEN
		.accessTokenConverter(accessTokenConverter())
		.reuseRefreshTokens(false)
		.userDetailsService(userDetailsService)
		.authenticationManager(authenticationManager);
	}
	
	@Bean //Tranformei em bean para quem precisar de um accesstoken pode usar
	public JwtAccessTokenConverter accessTokenConverter() {
		JwtAccessTokenConverter accessTokenConverter = new JwtAccessTokenConverter();
		accessTokenConverter.setSigningKey("algaworks");
	return accessTokenConverter;
}

	@Bean
	public TokenStore tokenStore() {
		return new JwtTokenStore(accessTokenConverter());
	}
	
}
