package br.com.fiap.agrosat;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class AgroSatApplication {

	public static void main(String[] args) {
		SpringApplication.run(AgroSatApplication.class, args);
	}

}
