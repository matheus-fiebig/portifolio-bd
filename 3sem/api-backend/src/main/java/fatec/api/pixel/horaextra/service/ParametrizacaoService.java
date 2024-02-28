package fatec.api.pixel.horaextra.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fatec.api.pixel.horaextra.dto.DadosParametrizacao;
import fatec.api.pixel.horaextra.dto.DadosParametrizacaoListagem;
import fatec.api.pixel.horaextra.model.Parametrizacao;
import fatec.api.pixel.horaextra.repository.ParametrizacaoRepository;

@Service
public class ParametrizacaoService {

	@Autowired
	private ParametrizacaoRepository repository;
	
	public void cadastrar(DadosParametrizacao dados) {
		var parametrizacao = new Parametrizacao(dados);
		repository.save(parametrizacao);
	}
	
	public List<Parametrizacao> listar(){
		var parametrizacoes = repository.findAll();
		return parametrizacoes;
	}
	
	public void alterar(DadosParametrizacao dados, Long id) {
		var parametrizacao = repository.getReferenceById(id);
		parametrizacao.setDataInicioPagamento(dados.dataInicioPagamento());
		parametrizacao.setDataFimPagamento(dados.dataFimPagamento());
		parametrizacao.setInicioHorarioNoturno(dados.inicioHorarioNoturno());
		parametrizacao.setFimHorarioNoturno(dados.fimHorarioNoturno());
		parametrizacao.setV1601(dados.v1601());
		parametrizacao.setV1602(dados.v1602());
		parametrizacao.setV3000(dados.v3000());
		parametrizacao.setV3001(dados.v3001());
		parametrizacao.setV1809(dados.v1809());
		parametrizacao.setV3016(dados.v3016());
	}
}
