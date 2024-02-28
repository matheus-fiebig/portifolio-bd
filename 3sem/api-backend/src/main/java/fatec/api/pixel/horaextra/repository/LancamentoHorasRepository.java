package fatec.api.pixel.horaextra.repository;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import fatec.api.pixel.horaextra.model.LancamentoHoras;

public interface LancamentoHorasRepository extends JpaRepository<LancamentoHoras, Long>, CustomLancamentoHorasRepository{
	
	@Query("Select l from LancamentoHoras l where dataInicio >= :dataInicio and dataFim <= :dataFim")
	List<LancamentoHoras> findLancamentoHorasByDataInicioAndFim(@Param("dataInicio") Date dataInicio,@Param("dataFim") Date dataFim);
	
}
