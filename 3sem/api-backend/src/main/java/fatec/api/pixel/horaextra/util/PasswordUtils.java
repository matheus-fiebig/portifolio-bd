package fatec.api.pixel.horaextra.util;

import org.springframework.security.crypto.bcrypt.BCrypt;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class PasswordUtils {

	public String encrypt (String password) {
		return BCrypt.hashpw(password, BCrypt.gensalt());
	}
}
