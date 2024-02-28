package fatec.api.pixel.horaextra.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import fatec.api.pixel.horaextra.model.TipoUsuario;
import fatec.api.pixel.horaextra.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

	Usuario findByCpf(String cpf);
	
	@Query("SELECT tipoUsuario FROM Usuario WHERE id = :idUsuario")
	TipoUsuario findTipoUsuarioByIdUsuario(@Param("idUsuario") Long idUsuario);
}