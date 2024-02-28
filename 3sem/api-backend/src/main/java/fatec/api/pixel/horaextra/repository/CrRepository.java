package fatec.api.pixel.horaextra.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import fatec.api.pixel.horaextra.model.Cr;

public interface CrRepository extends JpaRepository<Cr, Long>, CustomCrRepository{

}
