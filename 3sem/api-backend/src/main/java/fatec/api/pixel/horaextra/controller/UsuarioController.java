package fatec.api.pixel.horaextra.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import fatec.api.pixel.horaextra.dto.DadosCadastroUsuario;
import fatec.api.pixel.horaextra.model.Usuario;
import fatec.api.pixel.horaextra.repository.UsuarioRepository;
import fatec.api.pixel.horaextra.service.AutenticacaoUsuarioService;
import fatec.api.pixel.horaextra.service.UsuarioService;
import jakarta.transaction.Transactional;

@RestController
@RequestMapping("/usuario")
public class UsuarioController {

	@Autowired
	UsuarioRepository repository;
	
	@Autowired
	UsuarioService service;
	
	@Autowired
	AutenticacaoUsuarioService autenticacaoUsuarioService;
	
	/*
	@GetMapping
	public ResponseEntity<List<Usuario>> findAll() {
		List<Usuario> list = repository.findAll();
		return ResponseEntity.ok().body(list);
	}
	*/
	
	@GetMapping
	public ResponseEntity<List<DadosCadastroUsuario>> listarUsuario(){
		var listagemUsuario = service.listarUsuario();
		return ResponseEntity.ok().body(listagemUsuario);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Usuario> findById(@PathVariable Long id) {
		Usuario user = repository.findById(id).get();
		return ResponseEntity.ok().body(user);
	}

	@PutMapping("/{id}")
	@Transactional
	public ResponseEntity alterarUsuario(@PathVariable Long id, @RequestBody DadosCadastroUsuario dados) {
		service.atualizarUsuario(dados, id);
		return ResponseEntity.ok().build();
	}
	
	@PostMapping
	@Transactional
	public ResponseEntity insert(@RequestBody DadosCadastroUsuario dados, UriComponentsBuilder uriBuilder) {
		var usuario = service.inserirUsuario(dados);
		var uri = uriBuilder.path("/usuario/{id}").buildAndExpand(usuario.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}
	
	@DeleteMapping("/{id}")
	@Transactional
	public ResponseEntity delete(@PathVariable Long id) {
		var usuario = repository.getReferenceById(id);
		service.excluirUsuario(usuario);
		return ResponseEntity.noContent().build();		
	}
}
