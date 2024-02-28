package fatec.api.pixel.horaextra.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fatec.api.pixel.horaextra.dto.DadosLoginUsuario;
import fatec.api.pixel.horaextra.dto.DadosRetornoLogin;
import fatec.api.pixel.horaextra.model.AutenticacaoUsuario;
import fatec.api.pixel.horaextra.service.AutenticacaoLoginService;
import fatec.api.pixel.horaextra.service.TokenService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/login")
@CrossOrigin(origins = "*") 
public class AutenticacaoLoginController {

	@Autowired
	private AuthenticationManager manager;
	
	@Autowired
	private TokenService tokenService;
	
	@Autowired
	AutenticacaoLoginService loginService;
	
	@PostMapping
	public ResponseEntity login(@RequestBody @Valid DadosLoginUsuario dados) {
		var token = new UsernamePasswordAuthenticationToken(dados.login(), dados.senha());
		var authentication = manager.authenticate(token);
		
		var tokenJWT = tokenService.gerarToken((AutenticacaoUsuario) authentication.getPrincipal());
		var permissaoUsuario = loginService.getDadosControlePermissao (dados.login());
		
		return ResponseEntity.ok(new DadosRetornoLogin(tokenJWT, permissaoUsuario.idTipoUsuario(), permissaoUsuario.id(), permissaoUsuario.flPrimeiroAcesso()));
	}                          
}
