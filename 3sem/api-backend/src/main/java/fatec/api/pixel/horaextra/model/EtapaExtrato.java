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

@Table(name = "Etapa_Extrato")
@Entity(name = "EtapaExtrato")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EtapaExtrato {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String descricao;
	
	@OneToMany(mappedBy = "etapa")
	private List<LancamentoHoras> lancamento;

	public EtapaExtrato(Long id) {
		this.id = id;
	}
}
