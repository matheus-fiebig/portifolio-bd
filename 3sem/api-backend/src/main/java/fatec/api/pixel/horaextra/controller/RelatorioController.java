package fatec.api.pixel.horaextra.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import fatec.api.pixel.horaextra.dto.DadosFiltroRelatorio;
import fatec.api.pixel.horaextra.service.RelatorioService;
import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("relatorio")
public class RelatorioController {

	@Autowired
	private RelatorioService relatorioService;
	
	@Autowired
	HttpServletResponse response;

	@GetMapping()
	public void generateExcelReport(@RequestParam(value="datainicio" , required = false) String dataInicio, 
			@RequestParam(value="datafim", required = false) String dataFim) throws Exception{
		
		response.setContentType("application/octet-stream");
		
		String headerKey = "Content-Disposition";
		String headerValue = "attachment;filename=extrato_hora.xls";

		response.setHeader(headerKey, headerValue);
		
		DadosFiltroRelatorio dados = new DadosFiltroRelatorio(dataInicio, dataFim);
		relatorioService.generateExcel(dados, response);
		
		response.flushBuffer();
	}

}
