package fatec.api.pixel.horaextra.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import fatec.api.pixel.horaextra.dto.DadosParametrizacao;
import fatec.api.pixel.horaextra.dto.DadosParametrizacaoListagem;
import fatec.api.pixel.horaextra.model.Parametrizacao;
import fatec.api.pixel.horaextra.service.ParametrizacaoService;
import jakarta.transaction.Transactional;

@RestController
@RequestMapping("/parametrizacao")
public class ParametrizacaoController {

	@Autowired
	private ParametrizacaoService service;
	
	/*
	@PostMapping
	public ResponseEntity cadastrar(@RequestBody DadosParametrizacao dados) {
		service.cadastrar(dados);
		return ResponseEntity.created(null).build();
	}
	*/
	
	@GetMapping
	public ResponseEntity<List<Parametrizacao>> listar(){
		var dados = service.listar();
		return ResponseEntity.ok().body(dados);
	}
	
	@PutMapping("/{id}")
	@Transactional
	public ResponseEntity alterar(@PathVariable Long id, @RequestBody DadosParametrizacao dados) {
		service.alterar(dados, id);
		return ResponseEntity.ok().build();
	}
}
