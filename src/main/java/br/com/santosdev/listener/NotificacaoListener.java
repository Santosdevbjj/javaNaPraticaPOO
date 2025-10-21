package br.com.santosdev.listener;

import br.com.santosdev.config.RabbitMQConfig;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * Componente que atua como Consumidor de Eventos.
 * Simula um processo assíncrono (ex: enviar email, registrar auditoria).
 */
@Component
public class NotificacaoListener {

    // A anotação RabbitListener escuta a fila configurada
    @RabbitListener(queues = RabbitMQConfig.QUEUE_NAME)
    public void processarNotificacao(String mensagem) {
        System.out.println("\n--- CONSUMIDOR RABBITMQ (Microsserviço de Auditoria) ---");
        System.out.println(" [AUDITORIA] Evento recebido...");
        System.out.println(" [INFO] Mensagem: " + mensagem);
        
        // Simulação de processamento (2 segundos) - não bloqueia a API REST
        try {
            Thread.sleep(2000); 
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        System.out.println(" [AUDITORIA] Processamento concluído com sucesso!");
    }
}
