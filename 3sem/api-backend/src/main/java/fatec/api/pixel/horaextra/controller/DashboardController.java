package fatec.api.pixel.horaextra.controller;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import fatec.api.pixel.horaextra.dto.DadosDashboard;
import fatec.api.pixel.horaextra.dto.DadosRetornoDashboard;
import fatec.api.pixel.horaextra.service.DashboardService;

@RestController
@RequestMapping("/dashboard")
public class DashboardController {
	
	@Autowired
	DashboardService service;
	
	@GetMapping
	public ResponseEntity<List<DadosRetornoDashboard>> findDashboard(@RequestParam(value="idCliente" , required = false) Long idCliente, 
							  										@RequestParam(value="idCr", required = false) Long idCr,								
							  										@RequestParam(value="dataInicio" , required = false) String dataInicio, 
							  										@RequestParam(value="dataFim", required = false) String dataFim) throws Exception{
		
		DadosDashboard dados = new DadosDashboard(idCliente, idCr, service.convertData(dataInicio), service.convertData(dataFim));
		var dadosRetorno = service.findDashboard(dados);
		return ResponseEntity.ok().body(dadosRetorno);	
	}
}
