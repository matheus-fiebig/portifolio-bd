package fatec.api.pixel.horaextra.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import fatec.api.pixel.horaextra.dto.DadosCadastroCrUsuario;
import fatec.api.pixel.horaextra.model.CrUsuario;
import fatec.api.pixel.horaextra.repository.CrUsuarioRepository;
import fatec.api.pixel.horaextra.service.CrUsuarioService;
import jakarta.transaction.Transactional;

@RestController()
@RequestMapping("/crUsuario")
@CrossOrigin(origins = "*")
public class CrUsuarioController {
	
	@Autowired
	private CrUsuarioRepository repository;
	
	@Autowired
	private CrUsuarioService service;
	
	
	@GetMapping
	public ResponseEntity<List<CrUsuario>> findAll() {
		List<CrUsuario> list = repository.findAll();
		return ResponseEntity.ok().body(list);
	}
	
	@GetMapping("/{idCr}")
	public ResponseEntity<List<CrUsuario>> listarCrUsuario(@PathVariable Long idCr){
		var listagemCrUsuario = service.listarCrUsuario(idCr);
		return ResponseEntity.ok().body(listagemCrUsuario);
	}
	
	@PostMapping
	@Transactional
	public ResponseEntity insert(@RequestBody List<DadosCadastroCrUsuario> dados, UriComponentsBuilder uriBuilder) {
		var crUsuario = service.inserirCrUsuario(dados);
		//var uri = uriBuilder.path("/crUsuario/{idUsuario}-{idCr}").buildAndExpand(crUsuario.getIdUsuario()).toUri();
		return ResponseEntity.created(null).build();
	}
	
	/*
	@PutMapping("/{id}")
	@Transactional
	public ResponseEntity update(@PathVariable Long id, @RequestBody DadosCadastroCrUsuario dados) {
		service.atualizarCrUsuario(dados, id, id);
		return ResponseEntity.ok().build();
	}
	*/
	
	@DeleteMapping
	public ResponseEntity delete(@RequestBody DadosCadastroCrUsuario dados) {
		service.excluirCrUsuario(dados);
		return ResponseEntity.ok().build();
	}
}
