package fatec.api.pixel.horaextra.model;

import fatec.api.pixel.horaextra.dto.DadosCadastroCrUsuario;
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

@Table(name = "Cr_Usuario")
@Entity(name = "Cr_Usuario")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CrUsuario {
	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(name = "Id_Usuario")
	private Long idUsuario;
	@Column(name = "Id_Cr")
	private Long idCr;
	
	public CrUsuario(DadosCadastroCrUsuario dados) {
		this.idUsuario = dados.idUsuario();
		this.idCr = dados.idCr();
	}
}
