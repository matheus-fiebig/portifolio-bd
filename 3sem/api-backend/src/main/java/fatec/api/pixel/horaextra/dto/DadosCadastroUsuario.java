package fatec.api.pixel.horaextra.dto;

public record DadosCadastroUsuario(Long id, Long idUsuario, Long idTipoUsuario, String cpf, String nome, String telefone, String email, boolean ativo) {

}
