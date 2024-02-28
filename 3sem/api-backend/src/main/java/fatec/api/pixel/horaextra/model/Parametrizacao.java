package fatec.api.pixel.horaextra.model;

import java.util.Date;

import fatec.api.pixel.horaextra.dto.DadosParametrizacao;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Table (name = "parametrizacao")
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Parametrizacao {

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(name = "dt_inicio_pagamento")
	private int dataInicioPagamento;
	@Column(name = "dt_fim_pagamento")
	private int dataFimPagamento;
	@Column(name = "inicio_horario_noturno")
	private String inicioHorarioNoturno;
	@Column(name = "fim_horario_noturno")
	private String fimHorarioNoturno;
	private int V1601;
	private int V1602;
	private int V3000;
	private int V3001;
	private int V1809;
	private int V3016;
	

	public Parametrizacao(DadosParametrizacao dados) {
		this.dataInicioPagamento = dados.dataInicioPagamento();
		this.dataFimPagamento = dados.dataFimPagamento();
		this.inicioHorarioNoturno = dados.inicioHorarioNoturno();
		this.fimHorarioNoturno = dados.fimHorarioNoturno();
		this.V1601 = dados.v1601();
		this.V1602 = dados.v1602();
		this.V3000 = dados.v3000();
		this.V3001 = dados.v3001();
		this.V1809 = dados.v1809();
		this.V3016 = dados.v3016();
	}
}
