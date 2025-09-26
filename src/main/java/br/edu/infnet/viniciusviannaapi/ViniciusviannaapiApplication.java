package br.edu.infnet.viniciusviannaapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class ViniciusviannaapiApplication {

	public static void main(String[] args) {
		SpringApplication.run(ViniciusviannaapiApplication.class, args);
	}

}
