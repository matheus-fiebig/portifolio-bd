package fatec.api.pixel.horaextra.util;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

public class LocalDateTimeConverter {

	public static LocalDateTime convertDateToLocalDateTime(Date date) {
		Instant instant = date.toInstant();
		LocalDateTime localDateTime = instant.atZone(ZoneId.systemDefault()).toLocalDateTime();
		return localDateTime;
	}
}
