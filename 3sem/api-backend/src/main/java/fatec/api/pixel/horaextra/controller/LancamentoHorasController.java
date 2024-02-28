package fatec.api.pixel.horaextra.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fatec.api.pixel.horaextra.dto.DadosAlteracaoStatusLancamento;
import fatec.api.pixel.horaextra.dto.DadosCadastroLancamentoHoras;
import fatec.api.pixel.horaextra.dto.DadosListagemLancamentoHoras;
import fatec.api.pixel.horaextra.model.LancamentoHoras;
import fatec.api.pixel.horaextra.repository.LancamentoHorasRepository;
import fatec.api.pixel.horaextra.service.EmailSenderService;
import fatec.api.pixel.horaextra.service.LancamentoHorasService;
import jakarta.transaction.Transactional;

@RestController
@RequestMapping("/lancamentoHoras")
@CrossOrigin(origins = "*") 
public class LancamentoHorasController {

	@Autowired
	private LancamentoHorasRepository repository;
	
	@Autowired
	LancamentoHorasService service;
	
	@PostMapping
	public ResponseEntity lancamento(@RequestBody DadosCadastroLancamentoHoras horas) {
		var lancamento = new LancamentoHoras(horas);
		repository.save(lancamento);
		service.enviarNotificacao(lancamento);
		return ResponseEntity.created(null).build();
	}

	@GetMapping
	public ResponseEntity<List<LancamentoHoras>> findAll() {
		List<LancamentoHoras> list = repository.findAll();
		return ResponseEntity.ok().body(list);
	}

	@GetMapping("/{idUsuario}")
	public ResponseEntity<List<DadosListagemLancamentoHoras>> getLancamentos(@PathVariable("idUsuario") Long id){
		var listagemLancamento  = service.listarLancamento(id);
		return ResponseEntity.ok().body(listagemLancamento);
	}
	
	@PutMapping
	@Transactional
	public ResponseEntity alterarStatus(@RequestBody DadosAlteracaoStatusLancamento dados) {
		service.alterarStatus(dados);
		return ResponseEntity.ok().build();
	}
}