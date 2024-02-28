package fatec.api.pixel.horaextra.repository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import fatec.api.pixel.horaextra.dto.DadosDashboard;
import fatec.api.pixel.horaextra.dto.DadosDashboardHoras;
import fatec.api.pixel.horaextra.dto.DadosLancamento;
import fatec.api.pixel.horaextra.dto.DadosListagemLancamentoHoras;
import fatec.api.pixel.horaextra.dto.DadosRetornoDashboard;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;

public class CustomLancamentoHorasRepositoryImpl implements CustomLancamentoHorasRepository {
	
	@PersistenceContext
	private EntityManager entityManager;
	
	private static final long ID_TIPO_ADMIN = 3L;
	private static final long ID_TIPO_GESTOR = 2L;
	private static final long ID_TIPO_COLABORADOR = 1L;
	
	public List<DadosListagemLancamentoHoras> findLancamentoHoras(Long idUsuario, Long idTipoUsuario){
		String jpql = "SELECT eh.Id,"
				+ "	   cr.Nome,"
				+ "       cliente.Razao_Social,"
				+ "       eh.Projeto,"
				+ "       eh.DataHora_Inicio,"
				+ "       modalidade.Descricao,"
				+ "       eh.DataHora_Fim,"
				+ "       usuario.Nome,"
				+ "       eh.Justificativa,"
				+ "       eh.Motivo,"
				+ "       eh.Id_Etapa_Extrato"
				+ " FROM extrato_hora eh"
				+ " INNER JOIN Cr cr on eh.Id_Cr = cr.Id"
				+ " INNER JOIN Cliente cliente on eh.Id_Cliente = cliente.Id"
				+ " INNER JOIN Modalidade modalidade on eh.Id_Modalidade = modalidade.Id"
				+ " INNER JOIN Usuario usuario on eh.Id_Usuario = usuario.Id";
		
				if(idTipoUsuario == ID_TIPO_COLABORADOR) {
					jpql += " WHERE usuario.Id = :idUsuario";
				}
				if(idTipoUsuario == ID_TIPO_GESTOR) {
					jpql += " INNER JOIN cr_usuario crUsuario on crUsuario.Id_Cr = cr.Id";
					jpql += " WHERE crUsuario.Id_Usuario = :idUsuario";
				}
				if(idTipoUsuario == ID_TIPO_ADMIN) {
					jpql += " WHERE eh.Id_Etapa_Extrato in (:etapas)";
				}
					
				TypedQuery<Object[]> query = (TypedQuery<Object[]>) entityManager.createNativeQuery(jpql, Object[].class);
				if(idTipoUsuario == ID_TIPO_COLABORADOR || idTipoUsuario == ID_TIPO_GESTOR) {
					query.setParameter("idUsuario", idUsuario);
				}else {
					query.setParameter("etapas", Arrays.asList(3,5,2));
				}
					
				
				List<Object[]> result = query.getResultList();
				List<DadosListagemLancamentoHoras> dadosListagemLancamentoHoras = new ArrayList<DadosListagemLancamentoHoras>();
				
				for(Object[] object : result) {
					dadosListagemLancamentoHoras.add(new DadosListagemLancamentoHoras(
							Long.valueOf(object[0].toString()) ,
							(String) object[1],
							(String) object[2],
							(String) object[3],
							(String) object[4].toString(), 
							(String) object[5], 
							(String) object[6].toString(),
							(String) object[7],
							(String) object[8],
							(String) object[9],
							Long.valueOf(object[10].toString())));
				}
				return dadosListagemLancamentoHoras;
	}
	
	public List<DadosRetornoDashboard> findHoras(DadosDashboard dados, DadosDashboardHoras horas){
		String jpql = "SELECT SUM(TIMESTAMPDIFF(HOUR,a.DataHora_Inicio, a.DataHora_Fim)) Horas,"
				+ "    b.Razao_Social,"
				+ "    c.Nome,"
				+ "    a.Id_Usuario,"
				+ "    a.Modalidade"
				+ "	   from ("
				+ "	   select"
				+ "			Id_Cliente,"
				+ "            Id_Cr,"
				+ "            Id_Modalidade,"
				+ "            Id_Usuario,"
				+ "            case"
				+ "				   when DATE_FORMAT(DataHora_Inicio, '%H:%i:s') >= :horarioNoturno and (DATE_FORMAT(DataHora_Fim , '%H:%i:s') <= :horarioMatutino or DATE_FORMAT(DataHora_Fim , '%H:%i:s') >= :horarioMatutino) and Id_Modalidade = 1 then 'HE Noturno'"
				+ "                when DATE_FORMAT(DataHora_Inicio, '%H:%i:s') < :horarioNoturno and DATE_FORMAT(DataHora_Fim , '%H:%i:s') > :horarioMatutino and Id_Modalidade = 1 then 'HE Diurno'"
				+ "                when Id_Modalidade = 2 then 'Sobreaviso'"
				+ "            end Modalidade,"
				+ "            DataHora_Inicio,"
				+ "            DataHora_Fim"
				+ "            from extrato_hora"
				+ " 	WHERE id_cliente = :idCliente"
				+ " 	AND id_cr = :idCr"
				+ " 	AND dataHora_inicio >= :dataInicio"
				+ " 	AND dataHora_fim <= :dataFim"
				+ " 	AND Id_Etapa_extrato = 2"
				+ " 	) as a"
				+ " 	join cliente b on a.Id_cliente = b.id"
				+ " 	join cr c on c.Id = a.id_cr"
				+ " 	join modalidade d on d.Id = a.id_Modalidade"
				+ " 	group by a.Modalidade, b.Razao_Social, c.Nome, d.Descricao, Id_Usuario"
				+ " 	order by a.Modalidade asc";
		
		TypedQuery<Object[]> query = (TypedQuery<Object[]>) entityManager.createNativeQuery(jpql, Object[].class);
		query.setParameter("horarioNoturno", horas.horarioNoturno());
		query.setParameter("horarioMatutino", horas.horarioMatutino());
		query.setParameter("idCliente", dados.idCliente());
		query.setParameter("idCr", dados.idCr());
		query.setParameter("dataInicio", dados.dataInicio());
		query.setParameter("dataFim", dados.dataFim());
		
		List<Object[]> result = query.getResultList();
		List<DadosRetornoDashboard> dadosRetornoDashboard = new ArrayList<DadosRetornoDashboard>();
		
		for(Object[] object : result) {
			dadosRetornoDashboard.add(new DadosRetornoDashboard(
					Double.valueOf(object[0].toString()),
					(String) object[1],
					(String) object[2],
					(int) object[3],
					(String) object[4]));
		}
		return dadosRetornoDashboard;
	}
	
	public DadosLancamento findDadosLancamentos(Long idUsuario, Long idCr){
		String jpql = "select u.Nome, c.Nome from extrato_hora e"
				+ " inner join usuario u on e.Id_Usuario = u.Id"
				+ " inner join cr c on e.Id_Cr = c.Id"
				+ " where c.Id = :idCr and u.Id = :idUsuario";
				
		
		TypedQuery<Object[]> query = (TypedQuery<Object[]>) entityManager.createNativeQuery(jpql, Object[].class);
		query.setParameter("idCr", idCr);
		query.setParameter("idUsuario", idUsuario);
		
		
		List<Object[]> result = query.getResultList();
		DadosLancamento dadosLancamento = null;
		
		for(Object[] object : result) {
			dadosLancamento = new DadosLancamento(object[0].toString(),object[1].toString());
		}
		return dadosLancamento;
	}
}