package fatec.api.pixel.horaextra.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import fatec.api.pixel.horaextra.dto.DadosCadastroCr;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Table(name = "Cr")
@Entity(name = "Cr")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Cr {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String nome;
	private String sigla;
	@Column(name = "Codigo_CR")
	private String codigo;
	private boolean ativo;
	
	@JsonIgnore
	@OneToMany(mappedBy = "cr")
	private List<LancamentoHoras> lancamento;
	
	/*
	@ManyToMany(mappedBy = "cr")
	private Set<Usuario> usuarios;
	*/
	
	public Cr(Long idCr) {
		this.id = idCr;
	}

	public Cr(DadosCadastroCr dados) {
		this.nome = dados.nomeCr();
		this.sigla = dados.siglaCr();
		this.codigo = dados.codigoCr();
		this.ativo = true;
	}
}
