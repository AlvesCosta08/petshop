package com.petshop.petshop;

import com.petshop.petshop.model.Cliente;
import com.petshop.petshop.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class PetshopApplication{

	public static void main(String[] args) {
		SpringApplication.run(PetshopApplication.class, args);
	}

}
