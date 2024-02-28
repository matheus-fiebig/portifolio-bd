package fatec.api.pixel.horaextra.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fatec.api.pixel.horaextra.dto.DadosCadastroCrUsuario;
import fatec.api.pixel.horaextra.model.CrUsuario;
import fatec.api.pixel.horaextra.repository.CrUsuarioRepository;

@Service
public class CrUsuarioService {
	
	@Autowired
	CrUsuarioRepository repository;
	
	
	public List<CrUsuario> listarCrUsuario(Long idCr){
		return repository.findCrUsuarioByIdCr(idCr);
	}
	
	public CrUsuario inserirCrUsuario(List<DadosCadastroCrUsuario> dados) {
		var crUsuario = new CrUsuario();
		for(DadosCadastroCrUsuario dado : dados) {
			crUsuario = new CrUsuario(dado);
			repository.save(crUsuario);
		}
		return crUsuario;
	}
	
	/*
	public void atualizarCrUsuario(DadosCadastroCrUsuario dados, Long idUsuario, Long idCr) {
		var atualizacaoCrUsuario = repository.getReferenceById(idUsuario);
		atualizacaoCrUsuario.setIdUsuario(dados.idUsuario());
		atualizacaoCrUsuario.setIdCr(dados.idCr());
	}
	*/
	
	public void excluirCrUsuario(DadosCadastroCrUsuario dados) {
		var crUsuario = repository.findCrUsuarioByIdCrAndIdUsuario(dados.idCr(), dados.idUsuario());
		repository.delete(crUsuario);
	}
}
