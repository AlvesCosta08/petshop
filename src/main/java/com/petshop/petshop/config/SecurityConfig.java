package com.petshop.petshop.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // Configura as regras de autorização para diferentes URLs
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/login", "/logout", "/", "/resources", "/static", "/css/**", "/js/**", "/images/**").permitAll() // Permite acesso público a essas URLs
                        .anyRequest().authenticated() // Requer autenticação para todas as outras URLs
                )
                // Configura o formulário de login
                .formLogin(form -> form
                        .loginPage("/login") // URL da página de login personalizada
                        .defaultSuccessUrl("/dashboard", true) // URL redirecionada após login bem-sucedido
                        .failureUrl("/login?error=true") // URL redirecionada após falha de login
                        .permitAll() // Permite acesso a todos à página de login
                )
                // Configura o comportamento de logout
                .logout(logout -> logout
                        .logoutUrl("/logout") // URL para logout
                        .logoutSuccessUrl("/logout.html") // URL redirecionada após logout bem-sucedido
                        .permitAll() // Permite acesso a todos à página de logout
                )
                // Configura a sessão
                .sessionManagement(session -> session
                        .sessionFixation().migrateSession() // Política de fixação de sessão
                        .invalidSessionUrl("/login?session=invalid") // URL redirecionada se a sessão for inválida
                        .maximumSessions(1) // Permite apenas uma sessão por usuário
                        .expiredUrl("/login?session=expired") // URL redirecionada se a sessão expirar
                )
                .sessionManagement(session -> session
                        .sessionFixation(sessionFixation -> sessionFixation.migrateSession()) // Política de fixação de sessão
                        .invalidSessionUrl("/login?session=invalid") // URL redirecionada se a sessão for inválida
                        .sessionConcurrency(concurrency -> concurrency
                                .maximumSessions(1) // Permite apenas uma sessão por usuário
                                .expiredUrl("/login?session=expired")) // URL redirecionada se a sessão expirar
                )
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED) // Política de criação de sessão
                );
        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        // Configura usuários em memória
        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
        manager.createUser(User.withUsername("squad3")
                .password(passwordEncoder().encode("123")) // Codifica a senha usando BCrypt
                .roles("ADMIN") // Define o papel do usuário como ADMIN
                .build());
        return manager;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        // Configura o encoder de senhas usando BCrypt
        return new BCryptPasswordEncoder();
    }
}