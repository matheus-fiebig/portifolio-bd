package fatec.api.pixel.horaextra.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fatec.api.pixel.horaextra.dto.DadosFiltroRelatorio;
import fatec.api.pixel.horaextra.dto.DadosRelatorio;
import fatec.api.pixel.horaextra.model.Cliente;
import fatec.api.pixel.horaextra.repository.ClienteRepository;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;

@Service
public class RelatorioService {

	@Autowired
	private CalculoVerbaService service;

	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	
	public void generateExcel(DadosFiltroRelatorio dados, HttpServletResponse response) throws Exception {

		Date dataInicio = sdf.parse(dados.dataInicio());
		Date dataFim = sdf.parse(dados.dataFim());
		
		
		List<DadosRelatorio> dadosRelatorio = service.getDadosRelatorio(dataInicio, dataFim); 

		
        Map<String, Map<String, Double>> agrupadoComSoma = dadosRelatorio.stream()
                .collect(Collectors.groupingBy(DadosRelatorio::nome,
                        Collectors.groupingBy(DadosRelatorio::verba,
                                Collectors.summingDouble(DadosRelatorio::quantidadeHoras))));
        
        dadosRelatorio.clear();
        
        agrupadoComSoma.forEach((nome, mapPorVerba) -> {
            mapPorVerba.forEach((verba, somaVerba) -> {
                dadosRelatorio.add(new DadosRelatorio(nome, verba, somaVerba));
            });
        });
		
		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet("Extrato Horas");
		HSSFRow row = sheet.createRow(0);

		row.createCell(0).setCellValue("Nome");
		row.createCell(1).setCellValue("Verba");
		row.createCell(2).setCellValue("Quantidade Horas");

		int dataRowIndex = 1;

		for (DadosRelatorio dado : dadosRelatorio) {
			HSSFRow dataRow = sheet.createRow(dataRowIndex);
			dataRow.createCell(0).setCellValue(dado.nome());
			dataRow.createCell(1).setCellValue(dado.verba());
			dataRow.createCell(2).setCellValue(dado.quantidadeHoras());
			dataRowIndex++;
		}

		ServletOutputStream ops = response.getOutputStream();
		workbook.write(ops);
		workbook.close();
		ops.close();

	}
}
