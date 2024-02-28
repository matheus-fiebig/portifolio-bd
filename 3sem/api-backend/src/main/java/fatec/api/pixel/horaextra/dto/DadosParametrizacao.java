package fatec.api.pixel.horaextra.dto;

import java.util.Date;

public record DadosParametrizacao(
		int dataInicioPagamento, 
		int dataFimPagamento,
		String inicioHorarioNoturno,
		String fimHorarioNoturno,
		int v1601,
		int v1602,
		int v3000,
		int v3001,
		int v1809,
		int v3016) {

}
