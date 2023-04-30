package in.stonecolddev.maliketh;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@ConfigurationPropertiesScan
@SpringBootApplication
public class MalikethApplication {

	public static void main(String[] args) {
		SpringApplication.run(MalikethApplication.class, args);
	}

}
