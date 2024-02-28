package fatec.api.pixel.horaextra.dto;

import java.time.LocalDate;
import java.util.Date;

public record DadosListagemLancamentoHoras(
		Long id,
		String cr,
		String cliente,
		String projeto,
		String inicio,
		String modalidade,
		String fim,
		String solicitante,
		String justificativa,
		String motivo,
		Long status) {
}
