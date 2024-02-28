package fatec.api.pixel.horaextra.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fatec.api.pixel.horaextra.dto.DadosListagemModalidade;
import fatec.api.pixel.horaextra.service.ModalidadeService;

@RestController()
@RequestMapping("/modalidade")
@CrossOrigin(origins = "*")
public class ModalidadeController {

	@Autowired
	private ModalidadeService service;
	
	@GetMapping
	public ResponseEntity<List<DadosListagemModalidade>> listarModalidade(){
		var listagemModalidade = service.listarModalidade();
		return ResponseEntity.ok().body(listagemModalidade);
	}
}