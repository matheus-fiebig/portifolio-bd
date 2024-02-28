package fatec.api.pixel.horaextra;

import javax.sql.DataSource;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

@SpringBootApplication
public class HoraextraApplication {

	public static void main(String[] args) {
		SpringApplication.run(HoraextraApplication.class, args);
	}
	
	@Bean
    public DataSource dataSouce(){
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setUrl("jdbc:mysql://localhost/api3sem");
        dataSource.setUsername("root");
        dataSource.setPassword("");
        return dataSource;
    }

}