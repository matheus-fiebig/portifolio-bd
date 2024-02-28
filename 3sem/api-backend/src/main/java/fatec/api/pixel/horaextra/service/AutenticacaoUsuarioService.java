package fatec.api.pixel.horaextra.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fatec.api.pixel.horaextra.model.AutenticacaoUsuario;
import fatec.api.pixel.horaextra.repository.AutenticacaoUsuarioRepository;
import fatec.api.pixel.horaextra.util.PasswordUtils;

@Service
public class AutenticacaoUsuarioService {

	private PasswordUtils passwordUtils = new PasswordUtils();
	
	@Autowired
	AutenticacaoUsuarioRepository repository;
	
	public AutenticacaoUsuario insert(String cpf) {
		var autenticacaoUsuario = new AutenticacaoUsuario(cpf, passwordUtils.encrypt(cpf));
		repository.save(autenticacaoUsuario);
		return autenticacaoUsuario;
	}
	
	public void update(String cpf, String senha) {
		var autenticacaoUsuario = repository.findByLoginIgnoreCase(cpf);
		autenticacaoUsuario.setSenha(passwordUtils.encrypt(senha));
		autenticacaoUsuario.setPrimeiroAcesso(false);
	}
}
