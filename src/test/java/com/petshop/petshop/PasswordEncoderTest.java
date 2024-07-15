package com.petshop.petshop;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordEncoderTest {
    public static void main(String[] args) {
        // Instanciar o BCryptPasswordEncoder
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        // Senha que você deseja codificar
        String rawPassword = "suaSenhaSegura";

        // Codificar a senha
        String encodedPassword = passwordEncoder.encode(rawPassword);

        // Imprimir a senha codificada
        System.out.println("Senha codificada: " + encodedPassword);

        // Agora você pode usar a senha codificada no banco de dados
    }
}
