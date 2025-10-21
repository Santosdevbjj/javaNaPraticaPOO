package br.com.santosdev.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    // 1. Define um usuário básico em memória (username: user, password: password)
    // Em produção, isso seria substituído por JDBC, LDAP ou JWT
    @Bean
    public InMemoryUserDetailsManager userDetailsService() {
        UserDetails user = User.withDefaultPasswordEncoder()
            .username("user")
            .password("password")
            .roles("USER")
            .build();
        return new InMemoryUserDetailsManager(user);
    }

    // 2. Configura a segurança de requisições HTTP
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable()) // Desabilita CSRF para APIs REST
            .authorizeHttpRequests(auth -> auth
                // Permite acesso irrestrito aos endpoints do Actuator (monitoramento)
                .requestMatchers("/actuator/**").permitAll() 
                // Exige autenticação (usuário/senha) para todos os outros endpoints
                .anyRequest().authenticated()
            )
            .httpBasic(httpBasic -> {}); // Habilita autenticação Basic no cabeçalho

        return http.build();
    }
}
