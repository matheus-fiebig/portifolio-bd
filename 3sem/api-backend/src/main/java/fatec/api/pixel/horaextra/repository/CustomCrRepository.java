package fatec.api.pixel.horaextra.repository;

import java.util.List;

import fatec.api.pixel.horaextra.dto.DadosListagemCr;

public interface CustomCrRepository {
	List<DadosListagemCr> findCrByIdUsuario(Long id);
}
