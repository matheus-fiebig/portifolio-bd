package fatec.api.pixel.horaextra.dto;

import java.util.Date;

public record DadosParametrizacaoListagem(
		Long id,
		Date dataInicioPagamento, 
		Date dataFimPagamento,
		String inicioHorarioNoturno,
		String fimHorarioNoturno) {

}
