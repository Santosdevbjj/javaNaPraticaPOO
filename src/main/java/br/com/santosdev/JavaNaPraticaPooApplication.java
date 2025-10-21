package br.com.santosdev;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Ponto de entrada do Microserviço Spring Boot.
 * A anotação @SpringBootApplication habilita a configuração automática,
 * como servidor web, injeção de dependência e auto-configuração de componentes.
 */
@SpringBootApplication
public class JavaNaPraticaPooApplication {

    public static void main(String[] args) {
        // Inicializa o container Spring, carrega as configurações e inicia o Tomcat.
        SpringApplication.run(JavaNaPraticaPooApplication.class, args);
        System.out.println("🚀 Microserviço Bancário POO iniciado na porta 8080. Acesse: http://localhost:8080/api/contas");
    }
}
