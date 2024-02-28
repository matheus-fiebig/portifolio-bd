package fatec.api.pixel.horaextra.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fatec.api.pixel.horaextra.dto.DadosLoginUsuario;
import fatec.api.pixel.horaextra.model.AutenticacaoUsuario;
import fatec.api.pixel.horaextra.service.AutenticacaoUsuarioService;
import fatec.api.pixel.horaextra.util.PasswordUtils;
import jakarta.transaction.Transactional;

@RestController
@RequestMapping("/autenticaoUsuario")
public class AutenticacaoUsuarioController {
	
	private PasswordUtils passwordUtils = new PasswordUtils();
	
	@Autowired
	AutenticacaoUsuarioService service;
	
	@PutMapping
	@Transactional
	public ResponseEntity alterarSenha(@RequestBody DadosLoginUsuario dados) {
		service.update(dados.login(), dados.senha());
		return ResponseEntity.ok().build();
	}
}
