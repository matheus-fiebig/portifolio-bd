package fatec.api.pixel.horaextra.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fatec.api.pixel.horaextra.dto.DadosListagemModalidade;
import fatec.api.pixel.horaextra.model.Modalidade;
import fatec.api.pixel.horaextra.repository.ModalidadeRepository;

@Service
public class ModalidadeService {
	
	@Autowired
	ModalidadeRepository repository;
	
	public List<DadosListagemModalidade> listarModalidade(){
		List<Modalidade> modalidades = repository.findAll();
		List<DadosListagemModalidade> dados = new ArrayList<DadosListagemModalidade>();
		
		for (Modalidade modalidade : modalidades) {
			dados.add(new DadosListagemModalidade(modalidade.getId(), modalidade.getDescricao()));
		}
		return dados;
	}
}
