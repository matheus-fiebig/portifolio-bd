package fatec.api.pixel.horaextra.repository;

import java.util.ArrayList;
import java.util.List;

import fatec.api.pixel.horaextra.dto.DadosListagemCr;
import fatec.api.pixel.horaextra.dto.DadosPessoaisGestorCr;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;

public class CustomCrUsuarioRepositoryImpl implements CustomCrUsuarioRepository {

	@PersistenceContext
	private EntityManager entityManager;
	
	private static final Long TIPO_GESTOR = 2L;
	
	public DadosPessoaisGestorCr getDadosGestor(Long idCr) {
		String jpql = "SELECT usuario.Nome, usuario.Email"
				+ "    FROM Cr_Usuario crUsuario"
				+ "    INNER JOIN Usuario usuario on crUsuario.Id_Usuario = usuario.Id"
				+ "    WHERE Id_Cr = :idCr AND id_tipo_usuario = :idGestor ";
		
				
				TypedQuery<Object[]> query = (TypedQuery<Object[]>) entityManager.createNativeQuery(jpql, Object[].class);
				query.setParameter("idCr", idCr);
				query.setParameter("idGestor", TIPO_GESTOR);
					
				List<Object[]> result = query.getResultList();
				DadosPessoaisGestorCr dados = null;
				
				for(Object[] object : result) {
					dados = new DadosPessoaisGestorCr(object[0].toString(), object[1].toString());
				}
				return dados;
	}

}
