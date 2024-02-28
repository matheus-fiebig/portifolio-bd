package fatec.api.pixel.horaextra.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

public record DadosCadastroLancamentoHoras(
		String projeto,
		Long idCr,
		Long idUsuario,
		Long modalidade,
		String motivo,
		LocalDateTime dataHoraInicio,
		LocalDateTime dataHoraFim,
		String justificativa,
		Long idCliente) {
	
}
