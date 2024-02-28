package fatec.api.pixel.horaextra.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fatec.api.pixel.horaextra.dto.DadosDashboard;
import fatec.api.pixel.horaextra.dto.DadosDashboardHoras;
import fatec.api.pixel.horaextra.dto.DadosRetornoDashboard;
import fatec.api.pixel.horaextra.repository.LancamentoHorasRepository;
import fatec.api.pixel.horaextra.repository.ParametrizacaoRepository;

@Service
public class DashboardService {

	@Autowired
	LancamentoHorasRepository repository;
	
	@Autowired
	ParametrizacaoRepository parametrizacaoRepository;
	
	public List<DadosRetornoDashboard> findDashboard(DadosDashboard dados){
		 List<DadosRetornoDashboard> dadosRetorno = new ArrayList<DadosRetornoDashboard>();
		 DadosDashboardHoras horas = new DadosDashboardHoras(horarioNoturno(), horarioMatutino());
		 
		 return dadosRetorno = repository.findHoras(dados, horas);
	}
	
	public Date convertData(String data) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = null;
		try {
			date = sdf.parse(data);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return date;
	}
	
	public String horarioNoturno() {
		return parametrizacaoRepository.findFimHorarioNoturno();
	}
	
	public String horarioMatutino() {
		return parametrizacaoRepository.findInicioHorarioNoturno();
	}
}
