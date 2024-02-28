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

import fatec.api.pixel.horaextra.dto.DadosCadastroCr;
import fatec.api.pixel.horaextra.dto.DadosListagemCr;
import fatec.api.pixel.horaextra.model.Cr;
import fatec.api.pixel.horaextra.repository.CrRepository;
import fatec.api.pixel.horaextra.service.CrService;
import jakarta.transaction.Transactional;

@RestController()
@RequestMapping("/cr")
@CrossOrigin(origins = "*") 
public class CrController {

	@Autowired
	private CrRepository repository;
	
	@Autowired
	private CrService service;
	
	@GetMapping
	public ResponseEntity<List<Cr>> findAll() {
		List<Cr> list = repository.findAll();
		return ResponseEntity.ok().body(list);
	}
	
	@GetMapping("/{idUsuario}")
	public ResponseEntity<List<DadosListagemCr>> listarCr(@PathVariable("idUsuario") Long idUsuario){
		var listagemCr = service.listarCr(idUsuario);
		
		return ResponseEntity.ok().body(listagemCr);
	}
	
	@PostMapping
	@Transactional
	public ResponseEntity insert(@RequestBody DadosCadastroCr dados, UriComponentsBuilder uriBuilder) {
		var cr = service.inserirCr(dados);
		var uri = uriBuilder.path("/cr/{id}").buildAndExpand(cr.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}
	
	@PutMapping("/{id}")
	@Transactional
	public ResponseEntity update(@PathVariable Long id, @RequestBody DadosCadastroCr dados) {
		service.atualizarCr(dados, id);
		return ResponseEntity.ok().build();
	}
	
	@DeleteMapping("/{id}")
	@Transactional
	public ResponseEntity delete(@PathVariable Long id) {
		var cr = repository.getReferenceById(id);
		service.excluirCr(cr);
		return ResponseEntity.noContent().build();		
	}
}
