package fatec.api.pixel.horaextra.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fatec.api.pixel.horaextra.dto.DadosAlteracaoStatusLancamento;
import fatec.api.pixel.horaextra.dto.DadosListagemLancamentoHoras;
import fatec.api.pixel.horaextra.model.EtapaExtrato;
import fatec.api.pixel.horaextra.model.LancamentoHoras;
import fatec.api.pixel.horaextra.repository.CrUsuarioRepository;
import fatec.api.pixel.horaextra.repository.LancamentoHorasRepository;
import fatec.api.pixel.horaextra.repository.UsuarioRepository;

@Service
public class LancamentoHorasService{
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private LancamentoHorasRepository lancamentoHorasRepository;
	
	@Autowired 
	private EmailSenderService emailService;
	
	@Autowired
	private CrUsuarioRepository crUsuarioRepository;
	
	public List<DadosListagemLancamentoHoras> listarLancamento(Long idUsuario){
		var tipoUsuario = usuarioRepository.findTipoUsuarioByIdUsuario(idUsuario);
		List<DadosListagemLancamentoHoras> lancamentoHoras = new ArrayList<DadosListagemLancamentoHoras>();
		return lancamentoHoras = lancamentoHorasRepository.findLancamentoHoras(idUsuario, tipoUsuario.getId());
	}
	
	public void alterarStatus(DadosAlteracaoStatusLancamento dados) {
		var lancamento = lancamentoHorasRepository.getReferenceById(dados.idLancamento());
		lancamento.setEtapa(new EtapaExtrato(dados.status()));
		lancamento.setJustificativa(dados.justificativa());
	}
	
	public void enviarNotificacao(LancamentoHoras lancamento) {
		var dadosGestor = crUsuarioRepository.getDadosGestor(lancamento.getCr().getId());
		var dadosLancamento = lancamentoHorasRepository.findDadosLancamentos(lancamento.getUsuario().getId(), lancamento.getCr().getId());
		emailService.sendSimpleEmail(dadosGestor.email(), "Olá " + dadosGestor.nome() + ", gostariamos de informá-lo que o colaborador(a)" + dadosLancamento.nomeUsuario() + " efetuou o lançamento de horas referente o periodo: "
				+ lancamento.getDataInicio().toString() + " - " + lancamento.getDataFim().toString() + " no CR " + dadosLancamento.nomeCr()+".");
	}
}