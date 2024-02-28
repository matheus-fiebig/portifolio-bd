package fatec.api.pixel.horaextra.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fatec.api.pixel.horaextra.dto.DadosCadastroCliente;
import fatec.api.pixel.horaextra.dto.DadosListagemCliente;
import fatec.api.pixel.horaextra.model.Cliente;
import fatec.api.pixel.horaextra.repository.ClienteRepository;

@Service
public class ClienteService {
	
	@Autowired
	ClienteRepository repository;
	
	public List<DadosListagemCliente> listarCliente(){
		List<Cliente> clientes = repository.findAll();
		List<DadosListagemCliente> dados = new ArrayList<DadosListagemCliente>();
		
		for (Cliente cliente : clientes) {
			dados.add(new DadosListagemCliente(
					cliente.getId(),
					cliente.getRazaoSocial(),
					cliente.getCnpj(),
					cliente.isAtivo()));
		}
		return dados;
	}
	
	public Cliente inserirCliente(DadosCadastroCliente dados) {
		var cliente = new Cliente(dados);
		repository.save(cliente);
		return cliente;
	}
	
	public void atualizarCliente(DadosCadastroCliente dados, Long id) {
		var atualizacaoCliente = repository.getReferenceById(id);
		atualizacaoCliente.setRazaoSocial(dados.razaoSocialCliente());
		atualizacaoCliente.setCnpj(dados.cnpjCliente());
	}
	
	public void excluirCliente(Cliente cliente) {
		cliente.setAtivo(false);
	}
}