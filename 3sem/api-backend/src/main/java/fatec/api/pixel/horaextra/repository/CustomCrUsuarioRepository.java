package fatec.api.pixel.horaextra.repository;

import fatec.api.pixel.horaextra.dto.DadosPessoaisGestorCr;

public interface CustomCrUsuarioRepository {

	DadosPessoaisGestorCr getDadosGestor(Long idCr);
}
