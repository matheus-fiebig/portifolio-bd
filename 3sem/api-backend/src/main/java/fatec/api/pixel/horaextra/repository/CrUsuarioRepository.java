package fatec.api.pixel.horaextra.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import fatec.api.pixel.horaextra.model.CrUsuario;

public interface CrUsuarioRepository extends JpaRepository<CrUsuario, Long>, CustomCrUsuarioRepository{

	public List<CrUsuario> findCrUsuarioByIdCr(Long idCr);
	
	public CrUsuario findCrUsuarioByIdCrAndIdUsuario(Long idCr, Long idUsuario);
	
}
