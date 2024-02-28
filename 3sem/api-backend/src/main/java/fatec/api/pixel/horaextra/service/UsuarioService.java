package fatec.api.pixel.horaextra.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fatec.api.pixel.horaextra.dto.DadosCadastroUsuario;
import fatec.api.pixel.horaextra.model.TipoUsuario;
import fatec.api.pixel.horaextra.model.Usuario;
import fatec.api.pixel.horaextra.repository.UsuarioRepository;

@Service
public class UsuarioService {

	@Autowired
	UsuarioRepository repository;
	
	@Autowired
	AutenticacaoUsuarioService autenticacaoUsuarioService;
	
	public List<DadosCadastroUsuario> listarUsuario(){
		List<Usuario> usuarios = repository.findAll();
		List<DadosCadastroUsuario> dados = new ArrayList<DadosCadastroUsuario>();
		
		for (Usuario usuario : usuarios) {
			dados.add(new DadosCadastroUsuario(
					usuario.getId(),
					usuario.getId(),
					usuario.getTipoUsuario().getId(),
					usuario.getCpf(),
					usuario.getNome(),
					usuario.getTelefone(),
					usuario.getEmail(),
					usuario.isAtivo()));
		}
		return dados;
	}
	
	public void atualizarUsuario(DadosCadastroUsuario dados, Long id) {
		var atualizacaoUsuario = repository.getReferenceById(id);
		atualizacaoUsuario.setTipoUsuario(new TipoUsuario(dados.idTipoUsuario()));
		atualizacaoUsuario.setCpf(dados.cpf());
		atualizacaoUsuario.setNome(dados.nome());
		atualizacaoUsuario.setTelefone(dados.telefone());
		atualizacaoUsuario.setEmail(dados.email());
	}
		
	public Usuario inserirUsuario(DadosCadastroUsuario dados) {
		var usuario = new Usuario(dados);
		var autenticacaoUsuario = autenticacaoUsuarioService.insert(dados.cpf());
		usuario.setAutenticacaoUsuario(autenticacaoUsuario);
		repository.save(usuario);
		return usuario;
	}
	
	public void excluirUsuario(Usuario usuario) {
		usuario.setAtivo(false);
	}
}
