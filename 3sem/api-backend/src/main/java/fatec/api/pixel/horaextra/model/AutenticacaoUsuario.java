package fatec.api.pixel.horaextra.model;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import fatec.api.pixel.horaextra.dto.DadosLoginUsuario;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Table(name = "autenticacao_usuario")
@Entity(name = "AutenticacaoUsuario")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AutenticacaoUsuario implements UserDetails{

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String login;
	private String senha;
	@Column(name = "fl_primeiro_acesso")
	private boolean primeiroAcesso;
	@OneToOne(mappedBy = "autenticacaoUsuario")
	private Usuario usuario;

	public AutenticacaoUsuario(String login, String senha) {
		this.login = login;
		this.senha = senha;
		this.primeiroAcesso = true;
	}

	//INSERIR OS NIVEIS DE PERFIL (Admin, gestor, etc).
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return List.of(new SimpleGrantedAuthority("ROLE_USER"));
	}

	@Override
	public String getPassword() {
		return this.senha;
	}

	@Override
	public String getUsername() {
		return this.login;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}
}
