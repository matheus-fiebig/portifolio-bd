package fatec.api.pixel.horaextra.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fatec.api.pixel.horaextra.dto.DadosCadastroCr;
import fatec.api.pixel.horaextra.dto.DadosListagemCr;
import fatec.api.pixel.horaextra.model.Cr;
import fatec.api.pixel.horaextra.repository.CrRepository;

@Service
public class CrService {

	@Autowired
	CrRepository repository;
	
	public List<DadosListagemCr> listarCr(Long idCr){
		return repository.findCrByIdUsuario(idCr);
	}
	
	public Cr inserirCr(DadosCadastroCr dados) {
		var cr = new Cr(dados);
		repository.save(cr);
		return cr;
	}
	
	public void atualizarCr(DadosCadastroCr dados, Long id) {
		var atualizacaoCr = repository.getReferenceById(id);
		atualizacaoCr.setNome(dados.nomeCr());
		atualizacaoCr.setSigla(dados.siglaCr());
		atualizacaoCr.setCodigo(dados.codigoCr());	
	}
	
	public void excluirCr(Cr cr) {
		cr.setAtivo(false);
	}
}
