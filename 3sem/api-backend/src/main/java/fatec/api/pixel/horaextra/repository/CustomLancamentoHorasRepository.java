package fatec.api.pixel.horaextra.repository;

import java.util.List;

import fatec.api.pixel.horaextra.dto.DadosDashboard;
import fatec.api.pixel.horaextra.dto.DadosDashboardHoras;
import fatec.api.pixel.horaextra.dto.DadosLancamento;
import fatec.api.pixel.horaextra.dto.DadosListagemLancamentoHoras;
import fatec.api.pixel.horaextra.dto.DadosRetornoDashboard;

public interface CustomLancamentoHorasRepository {
	List<DadosListagemLancamentoHoras> findLancamentoHoras(Long idUsuario, Long TipoUsuario);
	List<DadosRetornoDashboard> findHoras(DadosDashboard dados, DadosDashboardHoras horas);
	DadosLancamento findDadosLancamentos(Long idUsuario, Long idCr);
}
