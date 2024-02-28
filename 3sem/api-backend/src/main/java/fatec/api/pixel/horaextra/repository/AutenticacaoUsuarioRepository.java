package fatec.api.pixel.horaextra.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

import fatec.api.pixel.horaextra.model.AutenticacaoUsuario;

public interface AutenticacaoUsuarioRepository extends JpaRepository<AutenticacaoUsuario, Long> {

	UserDetails findByLogin(String login);
	AutenticacaoUsuario findByLoginIgnoreCase(String login);
}
