package co.agpaytech;

import co.agpaytech.rest.CountryController;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class AgpaytechTaskApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext applicationContext =  SpringApplication.run(AgpaytechTaskApplication.class, args);
	}

}
