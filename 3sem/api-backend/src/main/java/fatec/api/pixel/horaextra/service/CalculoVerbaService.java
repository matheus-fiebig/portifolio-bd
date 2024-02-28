package fatec.api.pixel.horaextra.service;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fatec.api.pixel.horaextra.dto.DadosRelatorio;
import fatec.api.pixel.horaextra.model.LancamentoHoras;
import fatec.api.pixel.horaextra.model.Modalidade;
import fatec.api.pixel.horaextra.repository.LancamentoHorasRepository;
import fatec.api.pixel.horaextra.repository.ParametrizacaoRepository;
import fatec.api.pixel.horaextra.util.LocalDateTimeConverter;

@Service
public class CalculoVerbaService {

	private static final Double FATOR_MULTIPLICACAO_HN = 1.1429d;
	
	private static final Long MODALIDADE_HORA_EXTRA = 1l;
	
	@Autowired
	private ParametrizacaoRepository parametrizacaoRepository;
	
	@Autowired
	private LancamentoHorasRepository lancamentoRepository;
	
	private LocalDateTimeConverter dateConverter = new LocalDateTimeConverter();
	
	public List<DadosRelatorio> getDadosRelatorio(Date dataInicio, Date dataFim){
		List<DadosRelatorio> dadosRelatorio = new ArrayList<DadosRelatorio>();
		List<LancamentoHoras> lancamentos = lancamentoRepository.findLancamentoHorasByDataInicioAndFim(dataInicio, dataFim);
		for(LancamentoHoras lancamento : lancamentos) {
			dadosRelatorio.addAll(calculoVerba(lancamento));
		}
		return dadosRelatorio;
	}
	
	
	public List<DadosRelatorio> calculoVerba(LancamentoHoras lancamento) {
		List<DadosRelatorio> dadosRelatorio = new ArrayList<DadosRelatorio>();
		quantidadeVerba1601(lancamento, dadosRelatorio);
		quantidadeVerba1602(lancamento, dadosRelatorio);
		quantidadeVerba3000(lancamento, dadosRelatorio);
		quantidadeVerba3001(lancamento, dadosRelatorio);
		return dadosRelatorio;
	}
	
	private boolean isHoraExtra(Modalidade modalidade) {
		if(modalidade.getId() == MODALIDADE_HORA_EXTRA ) return true;
		return false;
	}
	
	private boolean isDiaUtil(LocalDateTime data) {
		DayOfWeek diaDaSemana = data.getDayOfWeek();
		return diaDaSemana != DayOfWeek.SATURDAY && diaDaSemana != DayOfWeek.SUNDAY;
	}
	
	private String getInicioHorarioNoturno() {
		return parametrizacaoRepository.findInicioHorarioNoturno().substring(0, 2);
	}
	
	private String getFimHorarioNoturno() {
		return parametrizacaoRepository.findFimHorarioNoturno().substring(0,2);
	}
	
	private boolean isNoturna(LocalDateTime data) {
		if(data.getHour() >= Integer.valueOf(getInicioHorarioNoturno())) {
			return true;
		}
		return false;
	}
	
	public void quantidadeVerba1601(LancamentoHoras lancamento, List<DadosRelatorio> dados) {
		LocalDateTime dataInicio = dateConverter.convertDateToLocalDateTime(lancamento.getDataInicio());
		LocalDateTime dataFim = dateConverter.convertDateToLocalDateTime(lancamento.getDataFim());
		if(!isDiaUtil(dataInicio) || isNoturna(dataInicio) || !isHoraExtra(lancamento.getModalidade())) {
			return;
		}
		if(quantidadeHorasTrabalhadas(dataInicio, dataFim) >= 2) {
			dados.add(new DadosRelatorio(lancamento.getUsuario().getNome(), "1601", 2d));
		}else {
			dados.add(new DadosRelatorio(lancamento.getUsuario().getNome(), "1601", 1d));
		}
	}
	
	public void quantidadeVerba1602(LancamentoHoras lancamento, List<DadosRelatorio> dados) {
		LocalDateTime dataInicio = dateConverter.convertDateToLocalDateTime(lancamento.getDataInicio());
		LocalDateTime dataFim = dateConverter.convertDateToLocalDateTime(lancamento.getDataFim());
		
		int horasTrabalhadas = quantidadeHorasTrabalhadas(dataInicio, dataFim);
		if(isNoturna(dataInicio) || horasTrabalhadas <= 2 || !isHoraExtra(lancamento.getModalidade())) {
			return;
		}
		if(isDiaUtil(dataInicio)){
			//retorna a quantidade de horas descontado das 2 horas da 1601
			dados.add(new DadosRelatorio(lancamento.getUsuario().getNome(), "1602", (double) horasTrabalhadas - 2));
		}else {
			dados.add(new DadosRelatorio(lancamento.getUsuario().getNome(), "1602", (double) horasTrabalhadas));
		}
		
	}
	
	public void quantidadeVerba3000(LancamentoHoras lancamento, List<DadosRelatorio> dados) {
		LocalDateTime dataInicio = dateConverter.convertDateToLocalDateTime(lancamento.getDataInicio());
		LocalDateTime dataFim = dateConverter.convertDateToLocalDateTime(lancamento.getDataFim());

		if (!isDiaUtil(dataInicio) || !isNoturna(dataInicio) || !isHoraExtra(lancamento.getModalidade())) {
			return;
		}
		if (quantidadeHorasTrabalhadas(dataInicio, dataFim) >= 2) {
			dados.add(new DadosRelatorio(lancamento.getUsuario().getNome(), "3000", 2 * FATOR_MULTIPLICACAO_HN));
			dados.add(new DadosRelatorio(lancamento.getUsuario().getNome(), "1809", 2 * FATOR_MULTIPLICACAO_HN));
		}else {
			dados.add(new DadosRelatorio(lancamento.getUsuario().getNome(), "3000", FATOR_MULTIPLICACAO_HN));
			dados.add(new DadosRelatorio(lancamento.getUsuario().getNome(), "1809", FATOR_MULTIPLICACAO_HN));
		}
		
	}
	
	public void quantidadeVerba3001(LancamentoHoras lancamento, List<DadosRelatorio> dados) {
		LocalDateTime dataInicio = dateConverter.convertDateToLocalDateTime(lancamento.getDataInicio());
		LocalDateTime dataFim = dateConverter.convertDateToLocalDateTime(lancamento.getDataFim());
		
		int horasTrabalhadas = quantidadeHorasTrabalhadas(dataInicio, dataFim);
		if(!isNoturna(dataInicio) || horasTrabalhadas <= 2 || !isHoraExtra(lancamento.getModalidade())) {
			return;
		}
		
		if(isDiaUtil(dataInicio)) {
			dados.add(new DadosRelatorio(lancamento.getUsuario().getNome(), "3001", (horasTrabalhadas - 2) * FATOR_MULTIPLICACAO_HN));
			dados.add(new DadosRelatorio(lancamento.getUsuario().getNome(), "1809", (horasTrabalhadas - 2) * FATOR_MULTIPLICACAO_HN));
		}else {
			dados.add(new DadosRelatorio(lancamento.getUsuario().getNome(), "3001", horasTrabalhadas * FATOR_MULTIPLICACAO_HN));
			dados.add(new DadosRelatorio(lancamento.getUsuario().getNome(), "1809", horasTrabalhadas * FATOR_MULTIPLICACAO_HN));
		}	
	}
	
	public void quantidadeVerba3016(LancamentoHoras lancamento, List<DadosRelatorio> dados) {
		LocalDateTime dataInicio = dateConverter.convertDateToLocalDateTime(lancamento.getDataInicio());
		LocalDateTime dataFim = dateConverter.convertDateToLocalDateTime(lancamento.getDataFim());
		
		int horasTrabalhadas = quantidadeHorasTrabalhadas(dataInicio, dataFim);
		if(isHoraExtra(lancamento.getModalidade())) {
			return;
		}

		dados.add(new DadosRelatorio(lancamento.getUsuario().getNome(), "3016", (double) horasTrabalhadas));
	}
	
	
	public int quantidadeHorasTrabalhadas(LocalDateTime dataInicio, LocalDateTime dataFim) {
		Duration duracao = Duration.between(dataInicio, dataFim);
		return (int) duracao.toHours();
	}
} 
