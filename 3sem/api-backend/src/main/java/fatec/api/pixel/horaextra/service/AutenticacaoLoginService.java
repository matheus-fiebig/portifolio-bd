package fatec.api.pixel.horaextra.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import fatec.api.pixel.horaextra.dto.DadosControlePermissao;
import fatec.api.pixel.horaextra.repository.AutenticacaoUsuarioRepository;
import fatec.api.pixel.horaextra.repository.UsuarioRepository;

@Service
public class AutenticacaoLoginService implements UserDetailsService {

	@Autowired
	private AutenticacaoUsuarioRepository repository;
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		return repository.findByLogin(username);
	}
	
	public DadosControlePermissao getDadosControlePermissao(String cpf) {
		var usuario = usuarioRepository.findByCpf(cpf);
		return new DadosControlePermissao(usuario.getTipoUsuario().getId(), usuario.getId(), convertToLong(usuario.getAutenticacaoUsuario().isPrimeiroAcesso()));
	}
	
	private Long convertToLong(boolean primeiroAcesso) {
		if(primeiroAcesso) {
			return 1l;
		}
		return 0l;
	}
}
