package fatec.api.pixel.horaextra.model;

import java.util.List;

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

@Table(name = "Modalidade")
@Entity(name = "Modalidade")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Modalidade {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String descricao;
	
	@OneToMany(mappedBy = "modalidade")
	private List<LancamentoHoras> lancamento;
	
	public Modalidade(Long modalidade) {
		this.id = modalidade;
	}
}
