package fatec.api.pixel.horaextra.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

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

@Table(name = "tipo_usuario")
@Entity(name = "TipoUsuario")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TipoUsuario {

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String descricao;
	
	@JsonIgnore
	@OneToMany(mappedBy = "tipoUsuario")
	
	private List<Usuario> usuario;
	
	public TipoUsuario(Long idTipoUsuario) {
		this.id = idTipoUsuario;
	}
}
