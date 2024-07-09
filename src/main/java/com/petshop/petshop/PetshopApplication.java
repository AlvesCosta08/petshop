package com.petshop.petshop;

import com.petshop.petshop.model.Cliente;
import com.petshop.petshop.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class PetshopApplication  implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(PetshopApplication.class, args);
	}
@Autowired
private ClienteRepository repository;
	@Override
	public void run(String... args) throws Exception {
		Cliente c1 = new Cliente();
		c1.setNome("Alves");
		c1.setEmail("alves@gmail.com");
		c1.setTelefone("8598988977");

		repository.save(c1);
	}
}
