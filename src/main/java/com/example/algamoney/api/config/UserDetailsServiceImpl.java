package com.example.algamoney.api.config;

import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.algamoney.api.model.Usuario;
import com.example.algamoney.api.repository.UsuarioRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private UsuarioRepository usuarioRepository;
	

	@Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    	
		PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
    	Optional<Usuario> usuarioOptional = usuarioRepository.findByEmail(email);
    	Usuario usuario = usuarioOptional.orElseThrow(()-> new UsernameNotFoundException("Usuario e/ou senha incorretos"));
    	
    	
        return new User(email, encoder.encode(usuario.getSenha()), getPermissoes(usuario));
    }

	private Collection<? extends GrantedAuthority> getPermissoes(Usuario usuario) {
		//SET USADO PRA RETORNAR UMA LISTA SEM DADOS REPETIDOS
		Set<SimpleGrantedAuthority> permissoes = new HashSet<>();
		
		//DENTRO DO USUARIO EXISTE UMA LISTA DE PERMISSOES, PARA CADA PERMISSÃO ENCONTRADA ELE VAI PEGAR A DESCRIÇÃO DELA REPRESENTADA POR "p" e adicionar a lista de permissões que criamos na linha anterior
		usuario.getPermissoes().forEach(p -> permissoes.add(new SimpleGrantedAuthority(p.getDescricao().toUpperCase())));
		return permissoes;
	}

}