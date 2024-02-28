package fatec.api.pixel.horaextra.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import fatec.api.pixel.horaextra.model.Parametrizacao;

public interface ParametrizacaoRepository extends JpaRepository<Parametrizacao, Long> {

	@Query("Select p.inicioHorarioNoturno from Parametrizacao p")
	public String findInicioHorarioNoturno();
	
	@Query("Select p.fimHorarioNoturno from Parametrizacao p")
	public String findFimHorarioNoturno();
	
}
