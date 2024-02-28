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

import fatec.api.pixel.horaextra.dto.DadosCadastroCliente;
import fatec.api.pixel.horaextra.dto.DadosListagemCliente;
import fatec.api.pixel.horaextra.model.Cliente;
import fatec.api.pixel.horaextra.repository.ClienteRepository;
import fatec.api.pixel.horaextra.service.ClienteService;
import jakarta.transaction.Transactional;

@RestController()
@RequestMapping("/cliente")
@CrossOrigin(origins = "*")
public class ClienteController {

	@Autowired
	private ClienteRepository repository;
	
	@Autowired
	private ClienteService service;
	
	/*
	@GetMapping
	public ResponseEntity<List<Cliente>> findAll() {
		List<Cliente> list = repository.findAll();
		return ResponseEntity.ok().body(list);
	}
	*/
	@GetMapping
	public ResponseEntity<List<DadosListagemCliente>> listarCliente(){
		var listagemCliente = service.listarCliente();
		return ResponseEntity.ok().body(listagemCliente);
	}

	@PostMapping
	@Transactional
	public ResponseEntity insert(@RequestBody DadosCadastroCliente dados, UriComponentsBuilder uriBuilder) {
		var cliente = service.inserirCliente(dados);
		var uri = uriBuilder.path("/cliente/{id}").buildAndExpand(cliente.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}
	
	@PutMapping("/{id}")
	@Transactional
	public ResponseEntity update(@PathVariable Long id, @RequestBody DadosCadastroCliente dados) {
		service.atualizarCliente(dados, id);
		return ResponseEntity.ok().build();
	}
	
	@DeleteMapping("/{id}")
	@Transactional
	public ResponseEntity delete(@PathVariable Long id) {
		var cliente = repository.getReferenceById(id);
		service.excluirCliente(cliente);
		return ResponseEntity.noContent().build();		
	}
}