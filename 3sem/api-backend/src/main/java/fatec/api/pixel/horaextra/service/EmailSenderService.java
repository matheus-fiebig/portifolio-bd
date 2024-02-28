package fatec.api.pixel.horaextra.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailSenderService {

	private static final String SUBJECT = "Alerta - Lançamento de Horas";
	
	@Autowired
	private JavaMailSender mailSender;

	public void sendSimpleEmail(String toEmail, String body) {
		SimpleMailMessage message = new SimpleMailMessage();
		message.setFrom("rpnotificacoesapi@gmail.com");
		message.setTo(toEmail);
		message.setText(body);
		message.setSubject(SUBJECT);
		mailSender.send(message);
	}

}
