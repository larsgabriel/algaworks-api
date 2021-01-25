package com.example.algamoney.api.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.expression.method.MethodSecurityExpressionHandler;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.provider.expression.OAuth2MethodSecurityExpressionHandler;
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

	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
		String encodedPassword = new BCryptPasswordEncoder().encode("@ngul@r0");
		clients.inMemory() //EM MEMORIA NÃO É BANCO DE DADOS
				//withClient = CLIENT/APLICAÇÃO/SERVIÇO QUE VAI PEDIR TOKEN, NO CASO EXEMPLO SERIA UMA APLICAÇÃO ANGULAR. AS INFORMAÇÕES
				//TEM QUE SER AS MESMAS DO POSTMAN. NO POSTMAN SERÁ CONFIGURADO UM POST COM ESTA URL localhost:8080/oauth/token
				//AUTHORIZATION TYPE BASIC AUTH USERNAME angular PASSWORD linha debaixo secret. Será então gerado um token
				.withClient("angular") 
				.secret(encodedPassword) //SENHA DO CLIENT
				.scopes("read", "write")
				//refresh_token é um granttype que implementa o refresh token.
				//QUANDO É GERADO UM PRIMEIRO TOKEN COMUM, ELE DURA 20 SEGUNDOS. MAS NA MESMA REQUISIÇÃO QUE VEM O TOKEN COMUM VEM UM CARA CHAMADO REFRESH TOKEN,
				//USANDO ESSE CARA PARA FAZER UMA NOVA REQUISIÇÃO ELE GERA UM NOVO TOKEN COM ID DE ACCESS_TOKEN QUE ESSE CARA PODE SER USADO NA REQUISIÇÃO
				//ISSO DA A SEGURANÇA PQ O ACCESS_TOKEN PODE SER ACESSADO VIA JS, PORÉM SE O REFRESH_TOKEN GERAR UM NOVO ACCESS_TOKEN PRA GENTE ELE É FEITO VIA COOKIE SEM ACESSO
				//VIA JS E O PROPRIO BROWSER RENOVA O ACCESS TOKEN PRA GENTE
				.authorizedGrantTypes("password", "refresh_token")
				//TEMPO DE DURAÇÃO DO TOKEN
				.accessTokenValiditySeconds(1800)
				//esse tempo de duração de refresh token.. esta conta da 1 dia, então o usuario vai poder ficar 1 dia sem ter que logar novamente.
				//O refresh token vai gerar um novo access token que não precisa de login e senha, ou seja o usuario não vai precisar se logar de novo, ele vai gerar um novo access token
				// e isso dentro de cockies não sera possivel pegar via javascript
				.refreshTokenValiditySeconds(3600 * 24)
			.and()
				.withClient("mobile")
				.secret("m0bil30")
				.scopes("read")
				.authorizedGrantTypes("passoword", "refresh_token")
				.accessTokenValiditySeconds(1800)
				.refreshTokenValiditySeconds(3600 * 24);
		
	}
	
	//ESTE MÉTODO PARA JWT verificar o token gerado no  https://jwt.io/
	@Override 
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
		endpoints
		.tokenStore(tokenStore())
		//CONVERSOR DE TOKEN
		.accessTokenConverter(accessTokenConverter())
		.reuseRefreshTokens(true)
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
	
	@Bean
	public MethodSecurityExpressionHandler createExpresionHandler() {
		return new OAuth2MethodSecurityExpressionHandler();
	}
	
	
}
