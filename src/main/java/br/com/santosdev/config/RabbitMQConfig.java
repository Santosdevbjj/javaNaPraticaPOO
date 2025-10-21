package br.com.santosdev.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuração das entidades do RabbitMQ: Exchange, Queue e Binding.
 */
@Configuration
public class RabbitMQConfig {
    
    public static final String EXCHANGE_NAME = "banco.exchange";
    public static final String QUEUE_NAME = "notificacao.conta.criada";
    public static final String ROUTING_KEY = "conta.criada";

    // Define o Exchange (ponto de distribuição/tópico)
    @Bean
    public TopicExchange exchange() {
        return new TopicExchange(EXCHANGE_NAME);
    }

    // Define a Fila (local onde as mensagens são entregues)
    @Bean
    public Queue queue() {
        return new Queue(QUEUE_NAME, true); // true = fila durável
    }

    // Liga a Fila ao Exchange através da Routing Key
    @Bean
    public Binding binding(Queue queue, TopicExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(ROUTING_KEY);
    }
}
