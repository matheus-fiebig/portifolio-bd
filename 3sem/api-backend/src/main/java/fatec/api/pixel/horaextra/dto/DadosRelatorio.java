package fatec.api.pixel.horaextra.dto;

public record DadosRelatorio(String nome, String verba, Double quantidadeHoras) {

	public DadosRelatorio(String nome, String verba, Double quantidadeHoras) {
		this.nome = nome;
		this.verba = verba;
		this.quantidadeHoras = quantidadeHoras;
	}

}
